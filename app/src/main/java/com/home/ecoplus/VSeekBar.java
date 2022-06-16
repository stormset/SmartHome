package com.home.ecoplus;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.switchbutton.widget.Utility;

import java.util.Objects;


public class VSeekBar extends View{
    private static final int CORNER_RADIUS_DP = 23;
    private static final int MAX = 100;
    private static final int MIN = 0;

    /**
     * The min value of progress value.
     */
    private int mMin = MIN;

    /**
     * The Maximum value that this SeekArc can be set to
     */
    private int mMax = MAX;

    /**
     * The increment/decrement value for each movement of progress.
     */
    private int mStep = 10;

    /**
     * The corner radius of the view.
     */
    private int mCornerRadius;

    /**
     * Text size in SP.
     */
    private float mTextSize = 26;

    /**
     * Text bottom padding in pixel.
     */
    private int mtextBottomPadding = 20;

    private int mPoints;

    private boolean mEnabled = true;
    /**
     * Enable or disable text .
     */
    private boolean mtextEnabled = true;

    /**
     * Enable or disable image .
     */
    private boolean mImageEnabled = false;

    /**
     * mTouchDisabled touches will not move the slider
     * only swipe motion will activate it
     */
    private boolean mTouchDisabled = true;
    private float mProgressSweep = 0;
    private Paint mProgressPaint;
    private Paint mTextPaint;
    private int scrWidth;
    private int scrHeight;
    private OnValuesChangeListener mOnValuesChangeListener;
    private int backgroundColor;
    private int mDefaultValue = 8;
    private Bitmap mDefaultImage;
    private Bitmap mMinImage;
    private Bitmap mMaxImage;
    private Rect dRect = new Rect();
    private boolean firstRun = true;
    private Paint paint;
    private Path path;
    private RectF pathRect;
    private float swipeRectWidth;
    private float swipeRectHeight;
    private float swipeRectTopPadding;
    private RectF swipeRect;
    private Paint swipeRectPaint;
    private int lastPoints = 0;
    private Context context;

    public VSeekBar(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public VSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        float density = getResources().getDisplayMetrics().density;
        paint = new Paint();
        swipeRectPaint = new Paint();
        paint.setAlpha(255);
        paint.setColor(backgroundColor);
        paint.setAntiAlias(true);
        path = new Path();
        pathRect = new RectF();
        swipeRect = new RectF();
        swipeRectPaint.setColor(ContextCompat.getColor(context, R.color.swipeRectColor));
        // Defaults, may need to link this into theme settings
        int progressColor = ContextCompat.getColor(context, R.color.white);
        backgroundColor = ContextCompat.getColor(context, R.color.colorOverlay);
        int textColor = ContextCompat.getColor(context, R.color.color_text);
        mTextSize = (int) (mTextSize * density);
        mDefaultValue = mMax/2;

        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.VSeekBar, 0, 0);
            mPoints = a.getInteger(R.styleable.VSeekBar_points, mPoints);
            mMax = a.getInteger(R.styleable.VSeekBar_max, mMax);
            mMin = a.getInteger(R.styleable.VSeekBar_min, mMin);
            mStep = a.getInteger(R.styleable.VSeekBar_step, mStep);
            mDefaultValue = a.getInteger(R.styleable.VSeekBar_defaultValue, mDefaultValue);
            mCornerRadius = a.getInteger(R.styleable.VSeekBar_mcornerRadius, mCornerRadius);
            mtextBottomPadding = a.getInteger(R.styleable.VSeekBar_textBottomPadding, mtextBottomPadding);
            mImageEnabled = a.getBoolean(R.styleable.VSeekBar_imageEnabled, mImageEnabled);
            if (mImageEnabled){
                mDefaultImage = ((BitmapDrawable) Objects.requireNonNull(a.getDrawable(R.styleable.VSeekBar_defaultImage))).getBitmap();
                mMinImage = ((BitmapDrawable) Objects.requireNonNull(a.getDrawable(R.styleable.VSeekBar_minImage))).getBitmap();
                mMaxImage = ((BitmapDrawable) Objects.requireNonNull(a.getDrawable(R.styleable.VSeekBar_maxImage))).getBitmap();
            }

            progressColor = a.getColor(R.styleable.VSeekBar_progressColor, progressColor);
            backgroundColor = a.getColor(R.styleable.VSeekBar_backgroundColor, backgroundColor);

            mTextSize = (int) a.getDimension(R.styleable.VSeekBar_textSize, mTextSize);
            textColor = a.getColor(R.styleable.VSeekBar_textColor, textColor);

            mEnabled = a.getBoolean(R.styleable.VSeekBar_enabled, mEnabled);
            mTouchDisabled = a.getBoolean(R.styleable.VSeekBar_touchDisabled, mTouchDisabled);
            mtextEnabled = a.getBoolean(R.styleable.VSeekBar_textEnabled, mtextEnabled);
            mPoints = mDefaultValue;
            a.recycle();
        }

        // range check
        mPoints = (mPoints > mMax) ? mMax : mPoints;
        mPoints = (mPoints < mMin) ? mMin : mPoints;

        mProgressPaint = new Paint();
        mProgressPaint.setColor(progressColor);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setColor(textColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mTextSize);

        scrHeight = context.getResources().getDisplayMetrics().heightPixels;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCornerRadius = (int)Utility.convertDpToPixel(CORNER_RADIUS_DP, getContext());
        swipeRectWidth = w/4.5f;
        swipeRectHeight = Utility.convertDpToPixel(4, getContext());
        swipeRectTopPadding = Utility.convertDpToPixel(5, getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        scrWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        scrHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        pathRect.set(0,0,scrWidth,scrHeight);
        mProgressPaint.setStrokeWidth(scrWidth);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        lastPoints = mPoints;
            canvas.translate(0, 0);
            path.reset();
            path.addRoundRect(pathRect, mCornerRadius, mCornerRadius, Path.Direction.CCW);
            canvas.clipPath(path, Region.Op.INTERSECT);
            paint.setColor(backgroundColor);
            canvas.drawRect(0, 0, scrWidth, scrHeight, paint);
            canvas.drawLine(canvas.getWidth() / 2, canvas.getHeight(), canvas.getWidth() / 2, mProgressSweep, mProgressPaint);
            float left = (canvas.getWidth()-swipeRectWidth)/2;
            float top = mProgressSweep + swipeRectTopPadding;
            swipeRect.set(left, top, left + swipeRectWidth,top + swipeRectHeight);
            canvas.drawRoundRect(swipeRect, 5, 5, swipeRectPaint);

        if (firstRun){
            firstRun = false;
            setValue(mPoints);
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mEnabled) {

            this.getParent().requestDisallowInterceptTouchEvent(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mOnValuesChangeListener != null)
                        mOnValuesChangeListener.onStartTrackingTouch(this);
                    if (!mTouchDisabled)
                        updateOnTouch(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    updateOnTouch(event);
                    break;
                case MotionEvent.ACTION_UP:
                    if (mOnValuesChangeListener != null)
                        mOnValuesChangeListener.onStopTrackingTouch(this);
                    setPressed(false);
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    if (mOnValuesChangeListener != null)
                        mOnValuesChangeListener.onStopTrackingTouch(this);
                    setPressed(false);
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return true;
        }
        return false;
    }

    /**
     * Update the UI components on touch events.
     *
     * @param event MotionEvent
     */
    private void updateOnTouch(MotionEvent event) {
        setPressed(true);
        double mTouch = convertTouchEventPoint(event.getY()-20);
        int progress = (int) Math.round(mTouch);
        lastPoints = mPoints;
        updateProgress(progress, true);
    }

    private double convertTouchEventPoint(float yPos) {
        float wReturn;

        if (yPos > (scrHeight *2)) {
            wReturn = scrHeight *2;
            return wReturn;
        }
        else if(yPos < 0){
            wReturn = 0;
        }
        else {
            wReturn =  yPos;
        }

        return wReturn;
    }

    private void updateProgress(int progress, boolean callListener) {
        mProgressSweep = progress;
        if (progress >= getProgressSweep(8)) {
            mProgressSweep = getProgressSweep(8);
        }
            progress = (progress > scrHeight) ? scrHeight : progress;
            progress = (progress < 0) ? 0 : progress;

            //convert progress to min-max range
        if (scrHeight + mMin > 0) mPoints = progress * (mMax - mMin) / scrHeight + mMin;
            //reverse value because progress is descending
            mPoints = mMax + mMin - mPoints;
            //if value is not max or min, apply step
            if (mPoints != mMax && mPoints != mMin) {
                mPoints = mPoints - (mPoints % mStep) + (mMin % mStep);
            }
        if (mPoints < 8)mPoints = 8;
            if (callListener && mOnValuesChangeListener != null && lastPoints != mPoints) {
                    mOnValuesChangeListener.onPointsChanged(this, mPoints-8);
            }
            if(mPoints!= lastPoints && !firstRun) {
                if (mPoints == 8){
                    final ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(),
                            0xFFFFFFFF,
                            0xff313131);

                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        @Override
                        public void onAnimationUpdate(final ValueAnimator animator) {
                            mProgressPaint.setColor((Integer) animator.getAnimatedValue());
                            invalidate();
                        }

                    });
                    valueAnimator.setDuration(300);
                    valueAnimator.start();

                }else if(lastPoints == 8 && mPoints>8){
                    final ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(),
                            0xff313131,
                            0xFFFFFFFF);

                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        @Override
                        public void onAnimationUpdate(final ValueAnimator animator) {
                            mProgressPaint.setColor((Integer) animator.getAnimatedValue());
                            invalidate();
                        }

                    });
                    valueAnimator.setDuration(300);
                    valueAnimator.start();
                }
                invalidate();
            }

    }

    /**
     * Gets a value, converts it to progress for the seekBar and updates it.
     * @param value The value given
     */

    private int getProgressSweep(int value){
        int mPoints = value;
        mPoints = (mPoints > mMax) ? mMax : mPoints;
        mPoints = (mPoints < mMin) ? mMin : mPoints;
        return scrHeight - ((mPoints - mMin) * scrHeight/(mMax - mMin));
    }

    public interface OnValuesChangeListener {
        /**
         * Notification that the point value has changed.
         *
         * @param vSeekBar The SwagPoints view whose value has changed
         * @param value     The current point value.
         */
        void onPointsChanged(VSeekBar vSeekBar, int value);
        void onStartTrackingTouch(VSeekBar vSeekBar);
        void onStopTrackingTouch(VSeekBar vSeekBar);
    }

    public void setValue(int value) {
        value += 7;
        if (value == 0)mProgressPaint.setColor(context.getResources().getColor(R.color.blackgray));
        else mProgressPaint.setColor(Color.WHITE);
        value = value > mMax ? mMax : value;
        value = value < mMin ? mMin : value;
        lastPoints = value;
        if (value == 0)value = mMin;
        updateProgress(getProgressSweep(value),false);
    }

    public void valueChangeAnimation (int value) {
        value += 7;
        value = value > mMax ? mMax : value;
        value = value < mMin ? mMin : value;
        int from,to;

        ValueAnimator va = ValueAnimator.ofInt(getValue(), value);
        int mDuration = 600; //in millis
        va.setDuration(mDuration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                updateProgress(getProgressSweep((int)animation.getAnimatedValue()), false);
            }
        });
        va.start();
    }


    public int getValue() {
        return mPoints;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
    }

    public int getMax() {
        return mMax;
    }

    public void setMax(int mMax) {
        mMax += 8;
        if (mMax <= mMin)
            throw new IllegalArgumentException("Max should not be less than zero");
        this.mMax = mMax;
    }

    public void setMin(int mMin) {
        if (mMax <= mMin)
            throw new IllegalArgumentException("Min should be less than max");
        this.mMin = mMin;
    }

    public void setCornerRadius(int mRadius) {
        this.mCornerRadius = mRadius;
        invalidate();
    }

    public int getCornerRadius() {
        return mCornerRadius;
    }

    public int getDefaultValue() {
        return mDefaultValue;
    }

    public void setDefaultValue(int mDefaultValue) {
        if (mDefaultValue > mMax)
            throw new IllegalArgumentException("Default value should not be bigger than max value.");
        this.mDefaultValue = mDefaultValue;

    }

    public int getStep() {
        return mStep;
    }

    public void setStep(int step) {
        mStep = step;
    }

    public boolean isImageEnabled() {
        return mImageEnabled;
    }

    public void setImageEnabled(boolean mImageEnabled) {
        this.mImageEnabled = mImageEnabled;
    }

    public void setOnValuesChangeListener(OnValuesChangeListener onValuesChangeListener) {
        mOnValuesChangeListener = onValuesChangeListener;
    }
}