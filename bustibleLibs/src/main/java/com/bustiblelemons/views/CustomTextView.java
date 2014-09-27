package com.bustiblelemons.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by bhm on 27.09.14.
 */
public class CustomTextView extends TextView implements CompoundButton.OnCheckedChangeListener {
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setSelected(isChecked);
    }
}
