package com.bustiblelemons.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
            valueBelow = statArray.getBoolean(R.styleable.StatisticView_valueBelow, valueBelow);
            if (valueBelow) {
                setValueParams();
            } else {
                setTitleParams();
            }
            TypedArray propArray = context.obtainStyledAttributes(attrs, R.styleable.PropertyView);
            isPercentile = propArray.getBoolean(R.styleable.PropertyView_percentile, false);
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

    private void setTitleParams() {
        LayoutParams params = new LayoutParams(titleView.getLayoutParams());
        params.addRule(RelativeLayout.BELOW, valueView.getId());
        titleView.setLayoutParams(params);
    }

    private void setValueParams() {
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
        /**
         * TODO Increase the value view by half of titleview font size
         */
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
