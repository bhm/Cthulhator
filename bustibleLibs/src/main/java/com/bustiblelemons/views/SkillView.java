package com.bustiblelemons.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
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
import com.bustiblelemons.logging.Logger;

import java.util.Locale;

/**
 * Created by bhm on 20.07.14.
 */
public class SkillView extends RelativeLayout implements View.OnClickListener {
    private static final Logger  log            = new Logger(SkillView.class);
    private              boolean isPercentile   = false;
    private              boolean valueLeft      = false;
    private              boolean hideTitle      = false;
    private              boolean showModifiers  = false;
    private              int     titleSizeResId = R.dimen.font_16;
    private              int     valueSizeResId = R.dimen.font_16;
    private              int     titleGravity   = Gravity.RIGHT;
    private              int     valueGravity   = Gravity.LEFT;
    private View            rootView;
    private TextView        titleView;
    private TextView        valueView;
    private ImageView       incView;
    private ImageView       decView;
    private int             valueSize;
    private int             titleSize;
    private int             defTitleSize;
    private int             defValSize;
    private Drawable        incDrawable;
    private Drawable        decDrawable;
    private OnClickListener cachedOnClick;
    private int maxValue = 100;
    private int value    = maxValue;
    private int minValue = 0;
    private SkillViewListener mChachedSkillViewListener;
    private final SkillViewListener mSkillViewListener = new SkillViewListener() {
        @Override
        public void onSkillValueClick(SkillView view) {
            if (mChachedSkillViewListener != null) {
                mChachedSkillViewListener.onSkillValueClick(view);
            }
        }

        @Override
        public void onSkillTitleClick(SkillView view) {
            if (mChachedSkillViewListener != null) {
                mChachedSkillViewListener.onSkillTitleClick(view);
            }
        }

        @Override
        public boolean onIncreaseClicked(SkillView view) {
            if (mChachedSkillViewListener != null) {
                mChachedSkillViewListener.onIncreaseClicked(view);
            }
            if (getIntValue() + 1 <= getMaxValue()) {
                view.setIntValue(value++);
                return true;
            }
            return false;
        }

        @Override
        public boolean onDecreaseClicked(SkillView view) {
            if (mChachedSkillViewListener != null) {
                mChachedSkillViewListener.onDecreaseClicked(view);
            }
            if (getIntValue() - 1 >= getMinValue()) {
                view.setIntValue(value--);
                return true;
            }
            return false;
        }
    };

    public SkillView(Context context) {
        super(context);
        setListener(mSkillViewListener);
        init(context, null);
    }

    public SkillView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        setListener(mSkillViewListener);
    }

    public SkillView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
        setListener(mSkillViewListener);
    }

    private void init(Context context, AttributeSet attrs) {
        rootView = LayoutInflater.from(context).inflate(R.layout.skill_view, this, true);
        valueView = (TextView) rootView.findViewById(android.R.id.custom);
        titleView = (TextView) rootView.findViewById(android.R.id.title);
        setupDefaultTextSize(context);
        incView = (ImageView) rootView.findViewById(R.id.inc);
        decView = (ImageView) rootView.findViewById(R.id.dec);
        setOnClicks();
        if (attrs != null) {
            TypedArray skillArray = context.obtainStyledAttributes(attrs, R.styleable.SkillView);
            setupModifiers(skillArray);
            valueLeft = skillArray.getBoolean(R.styleable.SkillView_valueLeft, valueLeft);
            int defValGravity = valueLeft ? Gravity.LEFT : Gravity.RIGHT;
            int defTitlteGravity = valueLeft ? Gravity.RIGHT : Gravity.LEFT;
            valueGravity = skillArray.getInteger(R.styleable.SkillView_titleGravity, defValGravity);
            titleGravity = skillArray.getInteger(R.styleable.SkillView_titleGravity,
                    defTitlteGravity);
            positionValue();
            isPercentile = skillArray.getBoolean(R.styleable.SkillView_percentile, false);
            hideTitle = skillArray.getBoolean(R.styleable.SkillView_hideTitle, hideTitle);
            setUpTextSize(skillArray);
            maxValue = skillArray.getInteger(R.styleable.SkillView_maxValue, maxValue);
            minValue = skillArray.getInteger(R.styleable.SkillView_maxValue, minValue);
            if (hideTitle) {
                hideTitle();
            }
            setTexts(skillArray);
            skillArray.recycle();
            skillArray.recycle();
        }
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    private void setOnClicks() {
        setOnClick(valueView, titleView, incView, decView);
    }

    private void setOnClick(View... views) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setOnClickListener(this);
                }
            }
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        cachedOnClick = l;
        super.setOnClickListener(this);
    }

    private void setTexts(TypedArray skillArray) {
        int val = skillArray.getInteger(R.styleable.SkillView_statValue, this.value);
        setIntValue(val);
        String title = skillArray.getString(R.styleable.SkillView_statTitle);
        setTitle(title);
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
        valueSize = propArray.getDimensionPixelSize(R.styleable.SkillView_valueSize, defValSize);
        titleSize = propArray.getDimensionPixelSize(R.styleable.SkillView_titleSize, defTitleSize);
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

    private void setValueLeftParams() {
        LayoutParams valueParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        valueParams.addRule(RIGHT_OF, decView.getId());
        valueView.setGravity(Gravity.CENTER_VERTICAL | valueGravity);
        valueView.setLayoutParams(valueParams);

        LayoutParams titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(LEFT_OF, incView.getId());
        titleParams.addRule(RIGHT_OF, valueView.getId());
        titleView.setGravity(Gravity.CENTER | titleGravity);
        titleView.setLayoutParams(titleParams);
    }

    private void setValueRightParams() {
        LayoutParams valueParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        valueParams.addRule(LEFT_OF, incView.getId());
        valueView.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        valueView.setLayoutParams(valueParams);

        LayoutParams titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RIGHT_OF, decView.getId());
        titleParams.addRule(LEFT_OF, valueView.getId());
        titleView.setLayoutParams(titleParams);
        titleView.setGravity(Gravity.CENTER | Gravity.LEFT);
    }

    public void setTitle(String title) {
        if (titleView != null) {
            titleView.setText(title);
        }
    }

    public int getIntValue() {
        return value;
    }

    public void setIntValue(int v) {
        setValue(v + "");
    }

    public void setStringValue(String value) {
        try {
            int v = Integer.valueOf(value);
            setIntValue(v);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void setValue(CharSequence s) {
        if (valueView != null) {
            String val = isPercentile ? String.format(Locale.ENGLISH, "%s%%", s) : s.toString();
            valueView.setText(val);
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

    public void setListener(SkillViewListener listener) {
        if (!listener.equals(mSkillViewListener)) {
            this.mChachedSkillViewListener = listener;
        }
    }

    @Override
    public void onClick(View view) {
        if (cachedOnClick != null) {
            cachedOnClick.onClick(view);
        }
        if (mSkillViewListener != null) {
            int id = view.getId();
            if (id == R.id.dec) {
                mSkillViewListener.onDecreaseClicked(this);
            } else if (id == R.id.inc) {
                mSkillViewListener.onIncreaseClicked(this);
            } else if (id == android.R.id.custom) {
                mSkillViewListener.onSkillValueClick(this);
            } else if (id == android.R.id.title) {
                mSkillViewListener.onSkillTitleClick(this);
            }
        }
    }

    public interface SkillViewListener extends PropertyViewListener<SkillView> {
    }
}
