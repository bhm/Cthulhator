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
    public static final int VALUE_TOP    = 1;
    public static final int VALUE_RIGHT  = 2;
    public static final int VALUE_BOTTOM = 3;
    public static final int VALUE_LEFT   = 4;
    private static int sDefValSize;
    private static int sDefTitleSize;
    private static int sDefaultStatValue = 99;
    private View     mRootView;
    private TextView mTitleView;
    private TextView mValueView;
    private boolean mIsPercentile = false;
    private boolean mValueIsBelow = false;
    private boolean mHideTitle    = false;
    private int valuePosition;
    private boolean showModifiers = false;
    private ImageView             mDecView;
    private ImageView             mIncView;
    private int                   mTitleSize;
    private int                   mValueSize;
    private Drawable              mIncDrawable;
    private Drawable              mDecDrawable;
    private OnClickListener       mCachedOnClick;
    private StatisticViewListener listener;
    private boolean               mValueBiggerIfTitleMissing;

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

    public void setValueBiggerIfTitleMissing(boolean valueBiggerIfTitleMissing) {
        this.mValueBiggerIfTitleMissing = valueBiggerIfTitleMissing;
    }

    private void init(Context context, AttributeSet attrs) {
        setupDefaultTextSize(context);
        mRootView = LayoutInflater.from(context).inflate(R.layout.statistic_view, this, true);
        mValueView = (TextView) mRootView.findViewById(android.R.id.custom);
        mTitleView = (TextView) mRootView.findViewById(android.R.id.title);
        mIncView = (ImageView) mRootView.findViewById(R.id.inc);
        mDecView = (ImageView) mRootView.findViewById(R.id.dec);
        setOnClick(mValueView, mTitleView, mIncView, mDecView);
        if (attrs != null) {
            TypedArray statArray = context.obtainStyledAttributes(attrs, R.styleable.StatisticView);
            positionValues(statArray);
            setupModifiers(statArray);
            mIsPercentile = statArray.getBoolean(R.styleable.StatisticView_percentile, false);
            mHideTitle = statArray.getBoolean(R.styleable.StatisticView_hideTitle, mHideTitle);
            mValueBiggerIfTitleMissing =
                    statArray.getBoolean(R.styleable.StatisticView_valueBiggerIfTitleMissing,
                            mValueBiggerIfTitleMissing);
            setUpTextSize(statArray);
            if (mHideTitle) {
                hideTitle();
            }
            int value = statArray.getInteger(R.styleable.StatisticView_statValue, sDefaultStatValue);
            setValue(value + "");
            String title = statArray.getString(R.styleable.StatisticView_statTitle);
            setTitle(title);
            statArray.recycle();
        }
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

    private void setupModifiers(TypedArray skillArray) {
        showModifiers = skillArray.getBoolean(R.styleable.SkillView_showModifiers, false);
//        setupIncreaseModifier(skillArray);
//        setupDecreaseDrawable(skillArray);
        if (showModifiers) {
            showModifers();
        } else {
            hideModifiers();
        }
    }

    private void setupIncreaseModifier(TypedArray array) {
        mIncDrawable = array.getDrawable(R.styleable.SkillView_increaseSrc);
        if (mIncDrawable == null) {
            mIncDrawable = getResources().getDrawable(R.drawable.increase_selector);
        }
        setIncreaseDrawable(mIncDrawable);
    }

    public void setIncreaseDrawable(Drawable drawable) {
        if (mIncView != null) {
            mIncView.setImageDrawable(drawable);
        }
    }

    private void setupDecreaseDrawable(TypedArray skillArray) {
        mDecDrawable = skillArray.getDrawable(R.styleable.SkillView_decreaseSrc);
        if (mDecDrawable == null) {
            mDecDrawable = getResources().getDrawable(R.drawable.decrease_selector);
        }
        setDecreaseDrawable(mDecDrawable);
    }

    public void setDecreaseDrawable(Drawable drawable) {
        if (mDecView != null) {
            mDecView.setImageDrawable(drawable);
        }
    }

    private void positionValues(TypedArray statArray) {
        mValueIsBelow = statArray.getBoolean(R.styleable.StatisticView_valueBelow, mValueIsBelow);
        if (mValueIsBelow) {
            setValueParams();
        } else {
            setTitleParams();
        }
    }

    private void setUpTextSize(TypedArray array) {
        mValueSize = array.getDimensionPixelSize(R.styleable.StatisticView_valueSize,
                sDefValSize);
        mTitleSize = array.getDimensionPixelSize(R.styleable.StatisticView_titleSize,
                sDefTitleSize);
        setValueSize(mValueSize);
        setTitleSize(mTitleSize);
    }

    public void setValueSize(int size) {
        if (mValueView != null) {
            mValueView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    public void setTitleSize(int size) {
        if (mTitleView != null) {
            mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    private void setupDefaultTextSize(Context context) {
        sDefTitleSize = context.getResources().getDimensionPixelSize(R.dimen.font_16);
        sDefValSize = context.getResources().getDimensionPixelSize(R.dimen.font_16);
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
        if (mDecView != null) {
            mDecView.setVisibility(VISIBLE);
            mDecView.setClickable(true);
        }
    }

    public void showIncreaser() {
        if (mIncView != null) {
            mIncView.setVisibility(VISIBLE);
            mIncView.setClickable(true);
        }
    }

    public void hideDecreaser() {
        if (mDecView != null) {
            mDecView.setVisibility(INVISIBLE);
            mDecView.setClickable(false);
        }
    }

    public void hideIncreaser() {
        if (mIncView != null) {
            mIncView.setVisibility(INVISIBLE);
            mIncView.setClickable(false);
        }
    }

    private void setTitleParams() {
        LayoutParams params = new LayoutParams(mTitleView.getLayoutParams());
        params.addRule(RelativeLayout.BELOW, mValueView.getId());
        mTitleView.setLayoutParams(params);
    }

    private void setValueParams() {
        LayoutParams params = new LayoutParams(mValueView.getLayoutParams());
        params.addRule(RelativeLayout.BELOW, mTitleView.getId());
        mValueView.setLayoutParams(params);
    }

    public void setTitle(String title) {
        if (mTitleView != null) {
            mTitleView.setText(title);
        }
    }

    public void setValue(CharSequence charSequence) {
        if (mValueView != null) {
            if (mIsPercentile) {
                mValueView.setText(String.format("%s%%", charSequence));
            } else {
                mValueView.setText(charSequence);
            }
        }
    }

    public void hideTitle() {
        if (mTitleView != null) {
            mTitleView.setVisibility(GONE);
        }
        if (mValueBiggerIfTitleMissing) {
            float titleSize = mTitleView.getTextSize();
            float halfTitle = titleSize / 2f;
            float resizedValueSize = mValueSize + halfTitle;
            setValueSize((int) resizedValueSize);

        }
    }

    public void showTitle() {
        if (mTitleView != null) {
            mTitleView.setVisibility(VISIBLE);
            if (mValueBiggerIfTitleMissing) {
                setValueSize(mValueSize);
            }
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mCachedOnClick = l;
        super.setOnClickListener(this);
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
                mCachedOnClick.onClick(view);
            }
        } else {
            if (mCachedOnClick != null) {
                mCachedOnClick.onClick(view);
            }
        }
    }

    public interface StatisticViewListener extends PropertyViewListener<StatisticView> {
    }
}
