package com.bustiblelemons.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bustiblelemons.bustiblelibs.R;

/**
 * Created by bhm on 20.07.14.
 */
public class StatisticView extends RelativeLayout implements View.OnClickListener {
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
    private boolean showModifiers = false;
    private ImageView       decView;
    private ImageView       incView;
    private int             defValSize;
    private int             defTitleSize;
    private int             titleSize;
    private int             valueSize;
    private Drawable        incDrawable;
    private Drawable        decDrawable;
    private OnClickListener cachedOnClick;
    private StatisticViewListener listener;

    public void setValueBiggerIfTitleMissing(boolean valueBiggerIfTitleMissing) {
        this.valueBiggerIfTitleMissing = valueBiggerIfTitleMissing;
    }

    private boolean valueBiggerIfTitleMissing;

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
        setupDefaultTextSize(context);
        rootView = LayoutInflater.from(context).inflate(R.layout.statistic_view, this, true);
        valueView = (TextView) rootView.findViewById(android.R.id.custom);
        titleView = (TextView) rootView.findViewById(android.R.id.title);
        incView = (ImageView) rootView.findViewById(R.id.inc);
        decView = (ImageView) rootView.findViewById(R.id.dec);
        setOnClick(valueView, titleView, incView, decView);
        if (attrs != null) {
            TypedArray statArray = context.obtainStyledAttributes(attrs, R.styleable.StatisticView);
            positionValues(statArray);
            setupModifiers(statArray);
            isPercentile = statArray.getBoolean(R.styleable.StatisticView_percentile, false);
            hideTitle = statArray.getBoolean(R.styleable.StatisticView_hideTitle, hideTitle);
            valueBiggerIfTitleMissing =
                    statArray.getBoolean(R.styleable.StatisticView_valueBiggerIfTitleMissing,
                            valueBiggerIfTitleMissing);
            setUpTextSize(statArray);
            if (hideTitle) {
                hideTitle();
            }
            String value = statArray.getString(R.styleable.StatisticView_statValue);
            setValue(value);
            String title = statArray.getString(R.styleable.StatisticView_statTitle);
            setTtitle(title);
            statArray.recycle();
        }
    }

    private void setOnClick(View... views) {
        if (views != null) {
            for(View view : views) {
                if (view != null) {
                    view.setOnClickListener(this);
                }
            }
        }
    }

    private void setupModifiers(TypedArray skillArray) {
        showModifiers = skillArray.getBoolean(R.styleable.SkillView_showModifiers, false);
        setupIncreaseModifier(skillArray);
        setIncreaseDrawable(incDrawable);
        setupDecreaseDrawablew(skillArray);
        if (showModifiers) {
            showModifers();
        } else {
            hideModifiers();
        }
    }

    private void setupDecreaseDrawablew(TypedArray skillArray) {
        decDrawable = skillArray.getDrawable(R.styleable.SkillView_decreaseSrc);
        if (decDrawable == null) {
            decDrawable = getResources().getDrawable(R.drawable.decrease_selector);
        }
        setDecreaseDrawable(decDrawable);
    }

    private void setupIncreaseModifier(TypedArray skillArray) {
        incDrawable = skillArray.getDrawable(R.styleable.SkillView_increaseSrc);
        if (incDrawable == null) {
            incDrawable = getResources().getDrawable(R.drawable.increase_selector);
        }
    }

    public void setDecreaseDrawable(Drawable drawable) {
        if (decView != null) {
            decView.setImageDrawable(drawable);
        }
    }

    public void setIncreaseDrawable(Drawable drawable) {
        if (incView != null) {
            incView.setImageDrawable(drawable);
        }
    }

    private void positionValues(TypedArray statArray) {
        valueBelow = statArray.getBoolean(R.styleable.StatisticView_valueBelow, valueBelow);
        if (valueBelow) {
            setValueParams();
        } else {
            setTitleParams();
        }
    }

    private void setUpTextSize(TypedArray propArray) {
        valueSize = propArray.getDimensionPixelSize(R.styleable.StatisticView_valueSize, defValSize);
        titleSize = propArray.getDimensionPixelSize(R.styleable.StatisticView_titleSize, defTitleSize);
        setValueSize(valueSize);
        setTitleSize(titleSize);
    }

    public void setValueSize(int size) {
        if (valueView != null) {
            valueView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    public void setTitleSize(int size) {
        if (titleView != null) {
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    private void setupDefaultTextSize(Context context) {
        defTitleSize = context.getResources().getDimensionPixelSize(R.dimen.font_16);
        defValSize = context.getResources().getDimensionPixelSize(R.dimen.font_16);
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
            decView.setClickable(true);
        }
    }

    public void showIncreaser() {
        if (incView != null) {
            incView.setVisibility(VISIBLE);
            incView.setClickable(true);
        }
    }

    public void hideDecreaser() {
        if (decView != null) {
            decView.setVisibility(INVISIBLE);
            decView.setClickable(false);
        }
    }

    public void hideIncreaser() {
        if (incView != null) {
            incView.setVisibility(INVISIBLE);
            incView.setClickable(false);
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
        if (valueBiggerIfTitleMissing) {
            float titleSize = titleView.getTextSize();
            float halfTitle = titleSize / 2f;
            float resizedValueSize = valueSize + halfTitle;
            setValueSize((int) resizedValueSize);

        }
    }

    public void showTitle() {
        if (titleView != null) {
            titleView.setVisibility(VISIBLE);
            if (valueBiggerIfTitleMissing) {
                setValueSize(valueSize);
            }
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        cachedOnClick = l;
        super.setOnClickListener(this);
    }

    public interface StatisticViewListener extends PropertyViewListener<StatisticView> {
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            int id = view.getId();
            if (id == R.id.dec) {
                listener.onDecreaseClicked(this);
            } else if (id == R.id.inc) {
                listener.onIncreaseClicked(this);
            } else if (id == android.R.id.custom) {
                listener.onSkillValueClick(this);
            } else if (id == android.R.id.title) {
                listener.onSkillTitleClick(this);
            } else {
                cachedOnClick.onClick(view);
            }
        } else {
            if (cachedOnClick != null) {
                cachedOnClick.onClick(view);
            }
        }
    }
}
