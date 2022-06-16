package com.home.ecoplus.RecycleViewAdapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class CustomCardView extends FrameLayout {
    Context mContext;

    public CustomCardView(Context context) {
        super(context);
        mContext = context;
    }

    public CustomCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public CustomCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ObjectAnimator.ofFloat(this, "scaleX", 1.05f).setDuration(30).start();
                ObjectAnimator.ofFloat(this, "scaleY", 1.05f).setDuration(30).start();
                break;
            case MotionEvent.ACTION_UP:
                    ObjectAnimator.ofFloat(this, "scaleX", 1f).setDuration(48).start();
                    ObjectAnimator.ofFloat(this, "scaleY", 1f).setDuration(48).start();
                break;
            case MotionEvent.ACTION_CANCEL:
                ObjectAnimator.ofFloat(this, "scaleX", 1f).setDuration(30).start();
                ObjectAnimator.ofFloat(this, "scaleY", 1f).setDuration(30).start();
                break;
        }
        return false;
    }

}

