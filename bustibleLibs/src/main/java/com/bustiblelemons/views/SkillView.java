package com.bustiblelemons.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bustiblelemons.bustiblelibs.R;

/**
 * Created by bhm on 20.07.14.
 */
public class SkillView extends RelativeLayout implements View.OnClickListener {
    private View     rootView;
    private TextView titleView;
    private TextView valueView;
    private boolean isPercentile  = false;
    private boolean valueLeft     = false;
    private boolean hideTitle     = false;
    private boolean showModifiers = false;
    private ImageView incView;
    private ImageView decView;
    private int titleSizeResId = R.dimen.font_16;
    private int valueSizeResId = R.dimen.font_16;
    private int valueSize;
    private int titleSize;
    private int defTitleSize;
    private int defValSize;


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
        setupDefaultTextSize(context);
        incView = (ImageView) rootView.findViewById(R.id.inc);
        decView = (ImageView) rootView.findViewById(R.id.dec);
        if (attrs != null) {
            TypedArray skillArray = context.obtainStyledAttributes(attrs, R.styleable.SkillView);
            showModifiers = skillArray.getBoolean(R.styleable.SkillView_showModifiers, false);
            if (showModifiers) {
                showModifers();
            } else {
                hideModifiers();
            }
            valueLeft = skillArray.getBoolean(R.styleable.SkillView_valueLeft, valueLeft);
            positionValue();
            TypedArray propArray = context.obtainStyledAttributes(attrs, R.styleable.PropertyView);
            isPercentile = propArray.getBoolean(R.styleable.PropertyView_percentile, false);
            hideTitle = propArray.getBoolean(R.styleable.PropertyView_hideTitle, hideTitle);
            setUpTextSize(propArray);
            if (hideTitle) {
                hideTitle();
            }
            String value = propArray.getString(R.styleable.PropertyView_statValue);
            setValue(value);
            String title = propArray.getString(R.styleable.PropertyView_statTitle);
            setTtitle(title);
            propArray.recycle();
            skillArray.recycle();
        }
    }

    private void setupDefaultTextSize(Context context) {
        defTitleSize = context.getResources().getDimensionPixelSize(R.dimen.font_16);
        defValSize = context.getResources().getDimensionPixelSize(R.dimen.font_16);
    }

    private void positionValue() {
        if (valueLeft) {
            setValueLeftParams();
        } else {
            setValueRightParams();
        }
    }

    private void setUpTextSize(TypedArray propArray) {
        valueSize = propArray.getDimensionPixelSize(R.styleable.PropertyView_valueSize, defValSize);
        titleSize = propArray.getDimensionPixelSize(R.styleable.PropertyView_titleSize, defTitleSize);
        setValueSize(valueSize);
        setTitleSize(titleSize);
    }

    public void setValueSize(int size) {
        if (valueView != null) {
            valueView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            realignViews();
        }
    }

    public void setTitleSize(int size) {
        if (titleView != null) {
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            realignViews();
        }
    }

    private void realignViews() {
        positionValue();
        if (titleView.getTextSize() <= valueView.getTextSize()) {
            realignToValue(titleView);
            realignToValue(decView);
            realignToValue(incView);
        } else {
            realignToTitle(valueView);
            realignToTitle(decView);
            realignToTitle(incView);
        }
    }

    private void realignToValue(View view) {
        LayoutParams baseParams = (LayoutParams) view.getLayoutParams();
        baseParams.addRule(ALIGN_TOP, valueView.getId());
        baseParams.addRule(ALIGN_BOTTOM, valueView.getId());
        view.setLayoutParams(baseParams);
    }

    private void realignToTitle(View view) {
        LayoutParams baseParams = (LayoutParams) view.getLayoutParams();
        baseParams.addRule(ALIGN_TOP, titleView.getId());
        baseParams.addRule(ALIGN_BOTTOM, titleView.getId());
        view.setLayoutParams(baseParams);
    }


    public void showModifers() {
        showDecreaser();
        showIncreaser();
    }

    public void hideModifiers() {
        hideDecreaser();
        hideIncreaser();
    }

    public void showDecreaser() {
        if (decView != null) {
            decView.setVisibility(VISIBLE);
        }
    }

    public void showIncreaser() {
        if (incView != null) {
            incView.setVisibility(VISIBLE);
        }
    }

    public void hideDecreaser() {
        if (decView != null) {
            decView.setVisibility(INVISIBLE);
        }
    }

    public void hideIncreaser() {
        if (incView != null) {
            incView.setVisibility(INVISIBLE);
        }
    }

    private void setValueLeftParams() {
        LayoutParams titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(LEFT_OF, incView.getId());
        titleParams.addRule(RIGHT_OF, valueView.getId());
        titleView.setGravity(Gravity.CENTER | Gravity.RIGHT);
        titleView.setLayoutParams(titleParams);

        LayoutParams valueParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        valueParams.addRule(RIGHT_OF, decView.getId());
        valueView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        valueView.setLayoutParams(valueParams);
    }

    private void setValueRightParams() {
        LayoutParams titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RIGHT_OF, decView.getId());
        titleParams.addRule(LEFT_OF, valueView.getId());
        titleView.setLayoutParams(titleParams);
        titleView.setGravity(Gravity.CENTER | Gravity.LEFT);

        LayoutParams valueParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        valueParams.addRule(LEFT_OF, incView.getId());
        valueView.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        valueView.setLayoutParams(valueParams);
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
            titleView.setVisibility(INVISIBLE);
        }
    }

    public void showTitle() {
        if (titleView != null) {
            titleView.setVisibility(VISIBLE);
        }
    }

    private SkillViewListener listener;

    public void setListener(SkillViewListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.dec) {

        } else if (id == R.id.inc) {

        } else if (id == android.R.id.custom && listener != null) {
            listener.onSkillValueClick(this);
        } else if (id == android.R.id.title && listener != null) {
            listener.onSkillTitleClick(this);
        }
    }

    public interface SkillViewListener {
        void onSkillValueClick(SkillView skillView);

        void onSkillTitleClick(SkillView skillView);
    }
}
