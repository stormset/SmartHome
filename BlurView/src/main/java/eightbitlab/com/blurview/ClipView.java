package eightbitlab.com.blurview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * FrameLayout that blurs its underlying content.
 * Can have children and draw them over blurred background.
 */
public class ClipView extends FrameLayout {
    private static final String TAG = ClipView.class.getSimpleName();
    @ColorInt
    private static final int TRANSPARENT = 0x00000000;
    Path mPath = new Path();
    RectF rectF = new RectF();
    float cornerRadius;
    int cornerRadiusDp = 12;

    BlurController blurController = createStubController();
    Paint paint;
    @ColorInt
    private int overlayColor;

    public ClipView(Context context) {
        super(context);
        init(null, 0);
    }

    public ClipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ClipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ClipView, defStyleAttr, 0);
        overlayColor = a.getColor(R.styleable.ClipView_clipOverlayColor, TRANSPARENT);
        a.recycle();
        paint = new Paint();
        paint.setColor(Color.WHITE);
        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(widthMode == MeasureSpec.UNSPECIFIED
                || widthMode == MeasureSpec.AT_MOST){
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY);
        }
        if(heightMode == MeasureSpec.UNSPECIFIED
                || heightMode == MeasureSpec.AT_MOST){
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.clipPath(mPath, Region.Op.INTERSECT);
        blurController.drawBlurredContent(canvas);
        canvas.drawColor(overlayColor);
        super.draw(canvas);
    }

    /**
     * Can be called to redraw blurred content manually
     */
    public void updateBlur() {
        invalidate();
    }

    /**
     * Can be used to stop blur auto update or resume if it was stopped before.
     * Enabled by default.
     */
    public ClipView setBlurAutoUpdate(final boolean enabled) {
        post(new Runnable() {
            @Override
            public void run() {
                blurController.setBlurAutoUpdate(enabled);
            }
        });
        return this;
    }

    /**
     * Enables/disables the blur. Enabled by default
     *
     * @param enabled true to enable, false otherwise
     */
    public ClipView setBlurEnabled(final boolean enabled) {
        post(new Runnable() {
            @Override
            public void run() {
                blurController.setBlurEnabled(enabled);
            }
        });
        return this;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF.set(0,0, w, h);
        cornerRadius = Utility.convertDpToPixel(cornerRadiusDp, getContext());
        mPath.reset();
        cornerRadius = Utility.convertDpToPixel(cornerRadiusDp, getContext());
        mPath.addRoundRect(rectF,cornerRadius,cornerRadius, Path.Direction.CCW);
        blurController.updateBlurViewSize();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        blurController.onDrawEnd(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        blurController.setBlurAutoUpdate(false);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        blurController.setBlurAutoUpdate(true);
    }

    private void setBlurController(@NonNull BlurController blurController) {
        this.blurController.destroy();
        this.blurController = blurController;
    }

    /**
     * Sets the color overlay to be drawn on top of blurred content
     *
     * @param overlayColor int color
     */
    public ClipView setOverlayColor(@ColorInt int overlayColor) {
        if (overlayColor != this.overlayColor) {
            this.overlayColor = overlayColor;
            invalidate();
        }
        return this;
    }

    /**
     * @param rootView Root View where BlurView's underlying content starts drawing.
     *                 Can be Activity's root content layout (android.R.id.content)
     *                 or (preferably) some of your root layouts.
     *                 BlurView's position will be calculated as a relative position to the rootView (not to the direct parent)
     *                 This means that BlurView will choose a content to blur based on this relative position.
     * @return {@link ClipView} to setup needed params.
     */
    public ClipView setupWith(@NonNull ViewGroup rootView, int scaleFactor, int cornerRadiusDp) {
        BlurController blurController = new ClipController(this, rootView, scaleFactor);
        this.cornerRadiusDp = cornerRadiusDp;
        setBlurController(blurController);
        return this;
    }

    public ClipView setupWith(@NonNull ViewGroup rootView, int scaleFactor) {
        BlurController blurController = new ClipController(this, rootView, scaleFactor);
        this.cornerRadiusDp = 12;
        setBlurController(blurController);
        return this;
    }

    //Used in edit mode and in case if no BlurController was set
    private BlurController createStubController() {
        return new BlurController() {
            @Override
            public void drawBlurredContent(Canvas canvas) {
            }

            @Override
            public void updateBlurViewSize() {
            }

            @Override
            public void onDrawEnd(Canvas canvas) {
            }

            @Override
            public void setBlurRadius(float radius) {
            }

            @Override
            public void setBlurAlgorithm(BlurAlgorithm algorithm) {
            }

            @Override
            public void setWindowBackground(@Nullable Drawable windowBackground) {
            }

            @Override
            public void destroy() {
            }

            @Override
            public void setBlurEnabled(boolean enabled) {
            }

            @Override
            public void setBlurAutoUpdate(boolean enabled) {
            }

            @Override
            public void setHasFixedTransformationMatrix(boolean hasFixedTransformationMatrix) {
            }
        };
    }
}
