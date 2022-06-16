package com.home.ecoplus;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;

public class MyDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Context context;
    public Dialog d;
    public TextView yes, no;

    public MyDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_box);
        this.getWindow().getAttributes().windowAnimations = R.style.MyDialog;
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        yes = (TextView) findViewById(R.id.btn_yes);
        no = (TextView) findViewById(R.id.details);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
               // context.finish();
                break;
            case R.id.details:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(120);
        view.startAnimation(animate);
    }
}