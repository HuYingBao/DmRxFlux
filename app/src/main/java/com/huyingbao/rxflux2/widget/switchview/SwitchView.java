package com.huyingbao.rxflux2.widget.switchview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;

import com.huyingbao.dm.R;

public class SwitchView extends AppCompatImageButton {
    private int padding = 10;

    private Drawable onDrawable;
    private Drawable offDrawable;
    private Drawable midDrawable;

    public SwitchView(@NonNull Context context) {
        this(context, null);
    }

    public SwitchView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        onDrawable = ContextCompat.getDrawable(context, R.drawable.ic_v_result_on);
        offDrawable = ContextCompat.getDrawable(context, R.drawable.ic_v_result_off);
        midDrawable = ContextCompat.getDrawable(context, R.drawable.ic_v_result_mid);
        init();
    }

    private void init() {
        setPadding(padding, padding, padding, padding);
        displayMid();
    }

    public void displayOff() {
        setImageDrawable(offDrawable);
    }

    public void displayOn() {
        setImageDrawable(onDrawable);
    }

    public void displayMid() {
        setImageDrawable(midDrawable);
    }
}
