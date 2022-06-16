package com.temperatureselector;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.temperatureselector.seekbar.R;

public class ProgressCircle extends View {
    private float mRingBias = 0.15f;
    private float mSectionRatio = 7.0f;
    private RectF mSectionRect = new RectF();
    protected float mSectionHeight;
    float currentTemp;
    protected float mRadius;
    protected int mMinProgress = 0;
    protected int mMaxProgress = 40;
    protected int mProgress = 0;

    protected float mCenterX;
    protected float mCenterY;

    private Paint mPaint = new Paint();
    Paint textPaint = new Paint();
    Paint sup = new Paint();
    private int mColor1 = Color.parseColor("#4B53FF");
    private int mColor2 = Color.parseColor("#ffff5900");
    private int mInactiveColor = Color.parseColor("#ffffff");

    {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public ProgressCircle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttributes(context, attrs);
    }

    public ProgressCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);
    }

    public ProgressCircle(Context context) {
        super(context);
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SeekCircle, 0, 0);
        try {
            // Read and clamp min
            int min = attributes.getInteger(R.styleable.SeekCircle_min, 0);
            mMinProgress = Math.max(min, 0);

            // Read and clamp max
            int max = attributes.getInteger(R.styleable.SeekCircle_max, 80);
            mMaxProgress = Math.max(max, 1);

            // Read and clamp progress
            int progress = attributes.getInteger(R.styleable.SeekCircle_progress, 25);
            mProgress = Math.max(Math.min(progress, mMaxProgress), 0);

            mColor1 = attributes.getColor(R.styleable.SeekCircle_startColor, Color.parseColor("#4B53FF"));
            mColor2 = attributes.getColor(R.styleable.SeekCircle_endColor, Color.parseColor("#fb3353"));
            mInactiveColor = attributes.getColor(R.styleable.SeekCircle_inactiveColor, Color.parseColor("#64ffffff"));

            mRingBias = attributes.getFloat(R.styleable.SeekCircle_ringBias, 0.15f);
            mSectionRatio = attributes.getFloat(R.styleable.SeekCircle_sectionRatio, 5.0f);
        } finally {
            attributes.recycle();
        }
    }

    private void updateDimensions(int width, int height) {
        // Update center position
        mCenterX = width / 2.0f;
        mCenterY = height / 2.0f;

        // Find shortest dimension
        int diameter = Math.min(width, height);
        float outerRadius = diameter / 2;
        float sectionHeight = outerRadius * mRingBias;
        float sectionWidth = sectionHeight / mSectionRatio;
        mRadius = outerRadius - sectionHeight / 2;
        mSectionRect.set(-sectionWidth / 2, -sectionHeight / 2, sectionWidth / 2, sectionHeight / 2);
        mSectionHeight = sectionHeight;
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (width > height)
            super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        else
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);

        updateDimensions(getWidth(), getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateDimensions(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Center our canvas
        canvas.rotate(218,mCenterX,mCenterY);
        canvas.translate(mCenterX, mCenterY);
        int relativeProgress = mProgress - mMinProgress;
        int relativeMax = 100;
        float rotation = 360.0f / (float) relativeMax;
        int width = getWidth();
        int height = getHeight();
        mCenterX = width / 2.0f;
        mCenterY = height / 2.0f;
        int diameter = Math.min(width, height);
        float outerRadius = diameter / 2;
        float sectionHeight = outerRadius * mRingBias;
        mSectionHeight = sectionHeight;
        float sectionWidth = sectionHeight / mSectionRatio;
        sectionWidth -= 4;
        mRadius = outerRadius - sectionHeight / 2;

        textPaint.setColor(Color.WHITE);
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(60);

        sup.setColor(Color.WHITE);
        sup.setFlags(Paint.ANTI_ALIAS_FLAG);
        sup.setTextSize(30);
        int currentTemperatureRelativePosition = (int)((Utility.roundToHalf(currentTemp))*2f);
        String temp = String.valueOf((float) currentTemperatureRelativePosition/2f);
        int decimal = Integer.parseInt(temp.substring(temp.indexOf ( "." ) + 1));
        String currentTempString = temp.substring(0, temp.indexOf("."));
        for (int i = 0; i < 80; ++i) {
            float rot = i * rotation;
            canvas.save();
            canvas.rotate((float) i * rotation);
            canvas.translate(0, -mRadius);

                if ((i >= currentTemperatureRelativePosition && i < relativeProgress) || (i <= currentTemperatureRelativePosition && i >= relativeProgress - 1)) {
                    mPaint.setColor(Color.WHITE);
                } else {
                    mPaint.setColor(mInactiveColor);
                }
                if (i == relativeProgress - 1) {
                    mSectionRect.set((-sectionWidth - 2f) / 2, -sectionHeight / 2, (sectionWidth + 2f) / 2, sectionHeight + 60 / 2);
                    if (i == currentTemperatureRelativePosition){
                        canvas.rotate(-(rot+218));
                        Point point = Utility.getTextPosition(rot,(int)sectionWidth,(int)sectionHeight, false);
                        canvas.drawText(currentTempString, point.x, point.y, textPaint);
                        if (decimal == 5) {
                            canvas.drawText("5", point.x + textPaint.measureText(currentTempString), point.y + textPaint.ascent() + 33, sup);
                        }
                        canvas.rotate(rot+218);
                    }
                }
                else if (i == currentTemperatureRelativePosition){
                    mSectionRect.set((-sectionWidth - 3.2f) / 2, -sectionHeight / 2, (sectionWidth + 3.2f) / 2, sectionHeight + 20 / 2);
                    canvas.rotate(-(rot+218));
                    if (currentTemperatureRelativePosition < relativeProgress) {
                        Point point = Utility.getTextPosition(rot,(int)sectionWidth,(int)sectionHeight, true);
                        canvas.drawText(currentTempString, point.x, point.y, textPaint);
                        if (decimal == 5) {
                            canvas.drawText("5", point.x + textPaint.measureText(currentTempString), point.y + textPaint.ascent() + 33, sup);
                        }
                    }else {
                        Point point = Utility.getTextPosition(rot,(int)sectionWidth,(int)sectionHeight, false);
                        canvas.drawText(currentTempString, point.x, point.y, textPaint);
                        if (decimal == 5) {
                            canvas.drawText("5", point.x + textPaint.measureText(currentTempString), point.y + textPaint.ascent() + 33, sup);
                        }
                    }
                    canvas.rotate(rot+218);
                }
                else {
                    mSectionRect.set(-sectionWidth / 2, -sectionHeight / 2, sectionWidth / 2, sectionHeight+20 / 2);
                }
                canvas.drawRoundRect(mSectionRect, 8f, 8f, mPaint);
                canvas.restore();
                updateDimensions(getWidth(), getHeight());
            }
            for (int i = 158; i < 300; i++) {
                canvas.save();
                canvas.rotate((float) (80 * rotation)+((i/2f)-80));
                canvas.translate(0, -mRadius);
                if (i>227&&i<=228) mPaint.setColor(Color.WHITE); //button selector
                else mPaint.setColor(mInactiveColor);
                mSectionRect.set((-sectionWidth) / 2, -sectionHeight / 2, (sectionWidth-3) / 2, sectionHeight + 20 / 2);
                canvas.drawRect(mSectionRect, mPaint);
                canvas.restore();
                updateDimensions(getWidth(), getHeight());
            }

        super.onDraw(canvas);
    }
    private float interpolate(float a, float b, float bias) {
        return (a + ((b - a) * bias));
    }

    private int interpolateColor(int colorA, int colorB, float bias) {
        float[] hsvColorA = new float[3];
        Color.colorToHSV(colorA, hsvColorA);

        float[] hsvColorB = new float[3];
        Color.colorToHSV(colorB, hsvColorB);

        hsvColorB[0] = interpolate(hsvColorA[0], hsvColorB[0], bias);
        hsvColorB[1] = interpolate(hsvColorA[1], hsvColorB[1], bias);
        hsvColorB[2] = interpolate(hsvColorA[2], hsvColorB[2], bias);

        // NOTE For some reason the method HSVToColor fail in edit mode. Just use the start color for now
        if (isInEditMode())
            return colorA;

        return Color.HSVToColor(hsvColorB);
    }
    /**
     * Get min progress
     *
     * @return Min progress
     */
    public float getMin() {
        return mMinProgress;
    }

    /**
     * Set min progress
     *
     * @param min
     */
    public void setMin(int min) {
        int newMin = Math.max(0, min);
        if (newMin != mMinProgress)
            mMinProgress = newMin;

        updateProgress(mProgress);
        invalidate();
    }

    /**
     * Get max progress
     *
     * @return Max progress
     */
    public float getMax() {
        return mMaxProgress;
    }

    /**
     * Set max progress
     *
     * @param max
     */
    public void setMax(int max) {
        int newMax = Math.max(max, 1);
        if (newMax != mMaxProgress)
            mMaxProgress = newMax;

        updateProgress(mProgress);
        invalidate();
    }

    /**
     * Get Progress
     *
     * @return progress
     */
    public int getProgress() {
        return mProgress-1;
    }

    /**
     * Set progress
     *
     * @param progress
     */
    public void setProgress(int progress) {
        updateProgress(progress);
    }

    /**
     * Get ratio
     *
     * @return progress ratio compared to max progress
     */
    public float getRatio() {
        int relativeMax = mMaxProgress - mMinProgress;
        return (float) (mProgress - mMinProgress) / (float) relativeMax;
    }

    public void setCurrentTemperature(float currentTemperature){
        currentTemp = currentTemperature;
        invalidate();
    }

    /**
     * Update progress internally. Clamp it to a valid range and invalidate the view if necessary
     *
     * @param progress
     * @return true if progress was changed and the view needs an update
     */
    protected boolean updateProgress(int progress) {
        // Clamp progress
        progress = Math.max(mMinProgress, Math.min(mMaxProgress, progress));

        if (progress != mProgress) {
            mProgress = progress;
            invalidate();

            return true;
        }

        return false;
    }
}
