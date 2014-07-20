package com.bustiblelemons.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bustiblelemons.bustiblelibs.R;

/**
 * Created by bhm on 20.07.14.
 */
public class SkillView extends RelativeLayout {
    private View     rootView;
    private TextView titleView;
    private TextView valueView;
    private boolean isPercentile = false;
    private boolean valueBelow   = false;
    private boolean hideTitle    = false;

    public SkillView(Context context) {
        super(context);
        init(context, null);
    }

    public SkillView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SkillView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rootView = LayoutInflater.from(context).inflate(R.layout.skill_view, this, true);
        valueView = (TextView) rootView.findViewById(android.R.id.custom);
        titleView = (TextView) rootView.findViewById(android.R.id.title);
        if (attrs != null) {
            TypedArray statArray = context.obtainStyledAttributes(attrs, R.styleable.StatisticView);
            isPercentile = statArray.getBoolean(R.styleable.StatisticView_percentile, false);
            valueBelow = statArray.getBoolean(R.styleable.StatisticView_valueBelow, valueBelow);
            if (valueBelow) {
                setVerticalParams();
            } else {
                setHorizontalParams();
            }
            TypedArray propArray = context.obtainStyledAttributes(attrs, R.styleable.PropertyView);
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

    private void setHorizontalParams() {
        LayoutParams titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(LEFT_OF, valueView.getId());
        titleView.setLayoutParams(titleParams);
        LayoutParams valueParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        valueParams.addRule(ALIGN_PARENT_RIGHT);
        titleView.setLayoutParams(valueParams);
    }

    private void setVerticalParams() {
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
