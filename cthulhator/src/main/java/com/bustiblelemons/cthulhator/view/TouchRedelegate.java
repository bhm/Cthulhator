package com.bustiblelemons.cthulhator.view;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by bhm on 05.08.14.
 */
public class TouchRedelegate implements View.OnTouchListener {
    private View mSender;
    private View mReceiver;

    public void setSender(View sender) {
        this.mSender = sender;
        if (mSender != null) {
            mSender.setOnTouchListener(this);
        }
    }

    public void setReceiver(View mReceiver) {
        this.mReceiver = mReceiver;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mReceiver != null) {
            return mReceiver.onTouchEvent(event);
        }
        return false;
    }
}
