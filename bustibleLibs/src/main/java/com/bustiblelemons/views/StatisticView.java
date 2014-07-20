package com.bustiblelemons.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bustiblelemons.bustiblelibs.R;

/**
 * Created by bhm on 20.07.14.
 */
public class StatisticView extends RelativeLayout {
    private View     rootView;
    private TextView titleView;
    private TextView valueView;
    private boolean isPercentile = false;
    private boolean valueBelow   = false;
    private boolean hideTitle    = false;

    public static final int VALUE_TOP    = 1;
    public static final int VALUE_RIGHT  = 2;
    public static final int VALUE_BOTTOM = 3;
    public static final int VALUE_LEFT   = 4;
    private int valuePosition;

    public StatisticView(Context context) {
        super(context);
        init(context, null);
    }

    public StatisticView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StatisticView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rootView = LayoutInflater.from(context).inflate(R.layout.statistic_view, this, true);
        valueView = (TextView) rootView.findViewById(android.R.id.custom);
        titleView = (TextView) rootView.findViewById(android.R.id.title);
        if (attrs != null) {
            TypedArray statArray = context.obtainStyledAttributes(attrs, R.styleable.StatisticView);
            isPercentile = statArray.getBoolean(R.styleable.StatisticView_percentile, false);
            valueBelow = statArray.getBoolean(R.styleable.StatisticView_valueBelow, valueBelow);
//            if (valueBelow) {
//                setValueBottom();
//            } else {
//                setValueTop();
//            }
            TypedArray propArray = context.obtainStyledAttributes(attrs, R.styleable.PropertyView);
            valuePosition = propArray.getInteger(R.styleable.PropertyView_valuePosition, VALUE_RIGHT);
            alignViews(valuePosition);
            hideTitle = propArray.getBoolean(R.styleable.PropertyView_hideTitle, hideTitle);
            if (hideTitle) {
                hideTitle();
            }
            String value = propArray.getString(R.styleable.PropertyView_statValue);
            setValue(value);
            String title = propArray.getString(R.styleable.PropertyView_statTitle);
            setTtitle(title);
        }
    }

    private void alignViews(int valuePosition) {
        switch(valuePosition) {
            case VALUE_TOP:
                setValueTop();
                break;
            case VALUE_BOTTOM:
                setValueBottom();
                break;
            case VALUE_RIGHT:
                setValueRight();
                break;
            case VALUE_LEFT:
                setValueLeft();
                break;
        }
    }

    private void setValueLeft() {
        LayoutParams titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RIGHT_OFg, titleView.getId());
        titleView.setGravity(Gravity.CENTER | Gravity.LEFT);
        titleView.setLayoutParams(titleParams);
        LayoutParams valueParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        valueParams.addRule(ALIGN_PARENT_LEFT);
        titleView.setLayoutParams(valueParams);
    }

    private void setValueRight() {
        LayoutParams titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(LEFT_OF, valueView.getId());
        titleParams.addRule(ALIGN_PARENT_RIGHT);
        titleView.setGravity(Gravity.CENTER | Gravity.RIGHT);
        titleView.setLayoutParams(titleParams);
        LayoutParams valueParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        valueParams.addRule(ALIGN_PARENT_RIGHT);
        titleView.setLayoutParams(valueParams);
    }

    public void setValueTop() {
        LayoutParams params = new LayoutParams(titleView.getLayoutParams());
        params.addRule(RelativeLayout.BELOW, valueView.getId());
        titleView.setLayoutParams(params);
    }

    public void setValueBottom() {
        LayoutParams params = new LayoutParams(valueView.getLayoutParams());
        params.addRule(RelativeLayout.BELOW, titleView.getId());
        valueView.setLayoutParams(params);
    }

    public void setTtitle(String title) {
        if (titleView != null) {
            titleView.setText(title);
        }
    }

    public void setValue(CharSequence charSequence) {
        if (valueView != null) {
            if (isPercentile) {
                valueView.setText(String.format("%s%%", charSequence));
            } else {
                valueView.setText(charSequence);
            }
        }
    }

    public void hideTitle() {
        if (titleView != null) {
            titleView.setVisibility(GONE);
        }
    }

    public void showTitle() {
        if (titleView != null) {
            titleView.setVisibility(VISIBLE);
        }
    }
}
