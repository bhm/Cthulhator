package com.bustiblelemons.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
    private int mValue   = maxValue;
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
            if (mChachedSkillViewListener != null &&
                    mChachedSkillViewListener.onIncreaseClicked(view)) {
                return true;
            }
            if (view.canIncrease()) {
                int nVal = getIntValue() + 1;
                view.setIntValue(nVal);
                return true;
            }
            return false;
        }

        @Override
        public boolean onDecreaseClicked(SkillView view) {
            if (mChachedSkillViewListener != null &&
                    mChachedSkillViewListener.onDecreaseClicked(view)) {
                return true;
            }
            if (canDecrease()) {
                int nVal = view.getIntValue() - 1;
                view.setIntValue(nVal);
                return true;
            }
            return false;
        }
    };
    private ViewGroup   listContainer;
    private ListAdapter mListAdapter;
    private DataSetObserver mObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            SkillView.this.rPopulateViews();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            SkillView.this.rPopulateViews();
        }
    };
    private int             mJump     = 1;

    public SkillView(Context context) {
        super(context);
        setSkillViewListener(mSkillViewListener);
        init(context, null);
    }

    public SkillView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        setSkillViewListener(mSkillViewListener);
    }


    public SkillView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
        setSkillViewListener(mSkillViewListener);
    }

    private void rRefreshViews() {
        if (mListAdapter != null && listContainer != null && listContainer.getChildCount() > 0) {
            for (int i = 0; i < listContainer.getChildCount(); i++) {
                View child = listContainer.getChildAt(i);
//                if (child != null)
            }
        }
    }

    private void rPopulateViews() {
        if (mListAdapter != null && listContainer != null) {
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View convertView = listContainer.getChildAt(i);
                View child = mListAdapter.getView(i, convertView, listContainer);
                if (child != null) {
                    if (child.getParent() == null) {
                        listContainer.addView(child);
                    }
                }
            }
        }
    }

    public boolean canDecrease() {
        return getIntValue() - mJump >= getMinValue();
    }

    public boolean canIncrease() {
        return getIntValue() + mJump <= getMaxValue();
    }

    private void init(Context context, AttributeSet attrs) {
        rootView = LayoutInflater.from(context).inflate(R.layout.skill_view, this, false);
        valueView = (TextView) rootView.findViewById(R.id.skillview_value);
        titleView = (TextView) rootView.findViewById(R.id.skillview_title);
        setupDefaultTextSize(context);
        incView = (ImageView) rootView.findViewById(R.id.inc);
        decView = (ImageView) rootView.findViewById(R.id.dec);
        listContainer = (ViewGroup) rootView.findViewById(R.id.list_container);
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
        }
        addView(rootView);
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
        int val = skillArray.getInteger(R.styleable.SkillView_statValue, this.mValue);
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

    public void setTitle(CharSequence title) {
        if (titleView != null) {
            titleView.setText(title);
        }
    }

    public void setTitle(int titleResId) {
        if (titleView != null) {
            titleView.setText(titleResId);
        }
    }

    public int getIntValue() {
        return mValue;
    }

    public void setIntValue(int v) {
        this.mValue = v;
        setValue(v + "");
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

    public void setSkillViewListener(SkillViewListener listener) {
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

    public void setIsPercentile(boolean percentile) {
        this.isPercentile = percentile;
    }

    public ListAdapter getAdapter() {
        return mListAdapter;
    }

    public void setAdapter(ListAdapter listAdapter) {
        if (isInEditMode()) {
            return;
        }
        if (mListAdapter != null) {
            mListAdapter.unregisterDataSetObserver(mObserver);
        }
        mListAdapter = listAdapter;
        mListAdapter.registerDataSetObserver(mObserver);
        rPopulateViews();
    }

    public interface SkillViewListener extends PropertyViewListener<SkillView> {
    }
}
