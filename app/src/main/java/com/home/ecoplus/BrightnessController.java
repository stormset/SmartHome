package com.home.ecoplus;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class BrightnessController extends RelativeLayout {
    Context context;
    View rootView;

    public BrightnessController(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BrightnessController(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        rootView = inflate(context, R.layout.root, this);

    }

}