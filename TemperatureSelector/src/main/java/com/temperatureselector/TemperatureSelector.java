package com.temperatureselector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.temperatureselector.seekbar.R;

public class TemperatureSelector extends RelativeLayout {
    Context context;
    View rootView;
    View minusButton;
    View plusButton;
    SeekCircle seekCircle;
    Handler repeatUpdateHandler;
    TextView targetTemperatureText;
    TextView targetTemperatureDecimalText;
    private OnTemperatureChangeListener onTemperatureChangeListener;
    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;
    private boolean autoIncremented = false;
    private boolean autoDecremented = false;

    public TemperatureSelector(Context context, float currentTemperature, float targetTemperature) {
        super(context);
        this.context = context;
        init();
        setCurrentTemperature(currentTemperature);
        setTargetTemperature(targetTemperature);
    }

    public TemperatureSelector(Context context, AttributeSet attrs, float currentTemperature, float targetTemperature) {
        super(context, attrs);
        this.context = context;
        init();
        setCurrentTemperature(currentTemperature);
        setTargetTemperature(targetTemperature);
    }

    private void init() {
        repeatUpdateHandler = new Handler();
        rootView = inflate(context, R.layout.root_view, this);
        targetTemperatureText = (TextView) rootView.findViewById(R.id.target_temperature_text);
        targetTemperatureDecimalText = (TextView) rootView.findViewById(R.id.target_temperature_decimal_text);
        seekCircle = (SeekCircle) rootView.findViewById(R.id.seekCircle);
        setTargetTemperature();
        plusButton = rootView.findViewById(R.id.more);
        minusButton = rootView.findViewById(R.id.less);

        final View parentPlus = (View) plusButton.getParent();
        parentPlus.post( new Runnable() {
            public void run() {
                final Rect rect = new Rect();
                plusButton.getHitRect(rect);
                rect.top -= 400;    // increase top hit area
                rect.left -= 400;   // increase left hit area
                rect.bottom += 400; // increase bottom hit area
                rect.right += 400;  // increase right hit area
                parentPlus.setTouchDelegate( new TouchDelegate( rect , plusButton));
            }
        });

        plusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!autoIncremented) increaseTemperature(0.5f);
                else autoIncremented = false;
            }
        });
        plusButton.setOnLongClickListener(
                new View.OnLongClickListener(){
                    public boolean onLongClick(View arg0) {
                        mAutoIncrement = true;
                        repeatUpdateHandler.post( new RptUpdater() );
                        return false;
                    }
                }
        );

        plusButton.setOnTouchListener( new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                if( (event.getAction()==MotionEvent.ACTION_UP || event.getAction()== MotionEvent.ACTION_CANCEL)
                        && mAutoIncrement ){
                    mAutoIncrement = false;
                }
                return false;
            }
        });
        minusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!autoDecremented) decreaseTemperature(0.5f);
                else autoDecremented = false;
            }
        });
        minusButton.setOnLongClickListener(
                new View.OnLongClickListener(){
                    public boolean onLongClick(View arg0) {
                        mAutoDecrement = true;
                        repeatUpdateHandler.post( new RptUpdater() );
                        return false;
                    }
                }
        );

        minusButton.setOnTouchListener( new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                if( (event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL)
                        && mAutoDecrement ){
                    mAutoDecrement = false;
                }
                return false;
            }
        });
        seekCircle.setCurrentTemperature(16.5f);
    }

    class RptUpdater implements Runnable {
        public void run() {
            if( mAutoIncrement ){
                autoIncremented = true;
                increaseTemperature(1);
                repeatUpdateHandler.postDelayed(new RptUpdater(), 200);
            }else if (mAutoDecrement){
                autoDecremented = true;
                decreaseTemperature(1);
                repeatUpdateHandler.postDelayed(new RptUpdater(), 200);
            }
        }
    }

    public void increaseTemperature(float by){
        if (seekCircle.getProgress()+(by*2) < 41*2) {
            seekCircle.updateProgress(seekCircle.getProgress() + (int)(by*2));
            float newVal = (float) seekCircle.getProgress()/2f;
            setTargetTemperature();
            if (onTemperatureChangeListener != null)onTemperatureChangeListener.onTemperatureChanged(newVal);
        }
    }

    public void decreaseTemperature(float by){
        if (seekCircle.getProgress()-(by*2) >= 0) {
            seekCircle.updateProgress(seekCircle.getProgress() - (int)(by*2));
            float newVal = (float) seekCircle.getProgress()/2f;
            setTargetTemperature();
            if (onTemperatureChangeListener != null)onTemperatureChangeListener.onTemperatureChanged(newVal);
        }
    }

    public void setOnTemperatureChangeListener(OnTemperatureChangeListener onTemperatureChangeListener){
        this.onTemperatureChangeListener = onTemperatureChangeListener;
    }
    public interface OnTemperatureChangeListener {
        void onTemperatureChanged(float temperature);
    }

    public void setTargetTemperature(float newVal){
        seekCircle.updateProgress((int)(Utility.roundToHalf(newVal)*2));
        setTargetTemperature();
    }

    public void setTargetTemperature(){
        String temp = String.valueOf((float) seekCircle.getProgress()/2f);
        int decimal = Integer.parseInt(temp.substring(temp.indexOf(".") + 1));
        String temperature = temp.substring(0, temp.indexOf("."));
        targetTemperatureText.setText(String.valueOf(temperature));
        if (decimal==5)targetTemperatureDecimalText.setVisibility(VISIBLE);
        else targetTemperatureDecimalText.setVisibility(INVISIBLE);
    }

    public void setCurrentTemperature(float currentTemperature){
        seekCircle.setCurrentTemperature(currentTemperature);
    }
}