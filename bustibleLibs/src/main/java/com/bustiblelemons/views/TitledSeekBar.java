package com.bustiblelemons.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bustiblelemons.bustiblelibs.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by bhm on 24.07.14.
 */
public class TitledSeekBar extends RelativeLayout implements SeekBar.OnSeekBarChangeListener {

    private static final int          DEFAULT_JUMP      = 10;
    private static final int          DEFAULT_MIN       = 0;
    private static final int          DEFAULT_MAX       = 100;
    private              int          jumpValue         = DEFAULT_JUMP;
    private              int          maxValue          = DEFAULT_MAX;
    private              int          seekBarMax        = maxValue;
    private              int          minValue          = DEFAULT_MIN;
    private              List<String> stringsCollection = new ArrayList<String>();
    private              DisplayMode  mode              = DisplayMode.DEFINED;
    private              boolean      compact           = false;
    private              boolean      isPercentile      = false;
    private View           rootView;
    private SeekBar        seekBar;
    private TextView       titleView;
    private String         title;
    private int            defTitleSize;
    private int            titleSize;
    private int            defValSize;
    private TextView       valueView;
    private int            valueSize;
    private String         value;
    private onValueChanged valueChanged;
    private int currentProgress = 0;

    public TitledSeekBar(Context context) {
        super(context);
        init(context, null);
    }

    public TitledSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TitledSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }


    public String getValue() {
        return value;
    }

    public void setValue(CharSequence value) {
        if (valueView != null) {
            this.value = value != null ? value.toString() : "";
            if (isPercentile) {
                valueView.setText(String.format("%s%%", value));
            } else {
                valueView.setText(value);
            }
        }
    }

    public int getProgress() {
        return seekBar != null ? seekBar.getProgress() : 0;
    }

    private void init(Context context, AttributeSet attrs) {
        setupDefaultTextSize(context);
        rootView = LayoutInflater.from(context).inflate(R.layout.titled_seekbar, this, true);
        seekBar = (SeekBar) rootView.findViewById(android.R.id.progress);
        if (seekBar != null) {
            seekBar.setOnSeekBarChangeListener(this);
        }
        titleView = (TextView) rootView.findViewById(android.R.id.title);
        valueView = (TextView) rootView.findViewById(android.R.id.custom);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitledSeekbar);
            compact = array.getBoolean(R.styleable.TitledSeekbar_compact, compact);
            if (compact) {
                setTitleLeft();
            }
            setupTitle(array);
            setupValueView(array);
            jumpValue = array.getInteger(R.styleable.TitledSeekbar_jumpValue, DEFAULT_JUMP);
            maxValue = array.getInteger(R.styleable.TitledSeekbar_maxValue, DEFAULT_MAX);
            minValue = array.getInteger(R.styleable.TitledSeekbar_minValue, DEFAULT_MIN);
            currentProgress = array.getInteger(R.styleable.TitledSeekbar_progress, 0);
            setMaxValue(maxValue);
            setMinValue(minValue);
            setJumpValue(jumpValue);
            seekBar.setProgress(currentProgress);
            setupValuesArray(array);
            array.recycle();
        }
    }

    private void setTitleLeft() {
        LayoutParams titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(ALIGN_PARENT_LEFT);
        titleView.setLayoutParams(titleParams);
        LayoutParams valueParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        valueParams.addRule(ALIGN_PARENT_RIGHT);
        valueParams.addRule(LEFT_OF, titleView.getId());
        valueView.setGravity(Gravity.CENTER);
        valueView.setLayoutParams(valueParams);
    }

    private void setupValuesArray(TypedArray array) {
        int valuesArrayId = array.getResourceId(R.styleable.TitledSeekbar_valuesArray, -1);
        if (valuesArrayId > 0) {
            String resName = "";
            if ("string-array".equalsIgnoreCase(resName)) {
                setStringsArray(valuesArrayId);
            } else if ("integer-array".equalsIgnoreCase(resName)) {
                setIntegersArray(valuesArrayId);
            } else {
                throw new IllegalArgumentException
                        ("Values array should only point to an array of integers or strings");
            }
        }
    }

    public void setStringsArray(int valuesArrayId) {
        String[] strings = getResources().getStringArray(valuesArrayId);
        setStringsArray(strings);
    }

    public void setStringsArray(String... strings) {
        if (strings != null) {
            List<String> tempStrings = new ArrayList<String>(strings.length);
            Collections.addAll(tempStrings, strings);
            setStringsCollection(tempStrings);
        }
    }

    public void setStringsCollection(Collection<String> strings) {
        this.stringsCollection = new ArrayList<String>(strings);
        setMinValue(0);
        setMaxValue(stringsCollection.size());
        setDisplayMode(DisplayMode.ARRAY);
    }

    public void setIntegersArray(int valuesArrayId) {
        int[] ints = getResources().getIntArray(valuesArrayId);
        setIntegersArray(ints);
    }

    public void setIntegersArray(int... ints) {
        if (ints != null) {
            Collection<String> strings = new ArrayList<String>();
            for (int i : ints) {
                strings.add("" + i);
            }
            setStringsCollection(strings);
        }
    }

    public void setJumpValue(int jumpValue) {
        this.jumpValue = jumpValue;
    }


    public void setMaxValue(int maxValue) {
        if (seekBar != null) {
            this.seekBarMax = calculateNewMax(this.jumpValue, this.minValue, maxValue);
            this.maxValue = maxValue;
            setDisplayMode(DisplayMode.DEFINED);
            seekBar.setMax(this.seekBarMax);
        }
    }

    public void setDisplayMode(DisplayMode mode) {
        this.mode = mode;
        if (mode.equals(DisplayMode.ARRAY)) {
            if (stringsCollection.size() == 9) {
                for (int it = minValue; it < maxValue; it += jumpValue) {
                    stringsCollection.add(it + "");
                }
            }
        }
    }

    private int calculateNewMax(int jumpValue, int minValue, int maxValue) {
        int difference = (maxValue - minValue);
        if (difference <= 0) {
            throw new IllegalArgumentException();
        }
        int newMax = difference / jumpValue;
        if (newMax <= 0) {
            throw new IllegalArgumentException();
        }
        return newMax;
    }

    private void setupValueView(TypedArray array) {
        valueSize = array.getDimensionPixelSize(R.styleable.TitledSeekbar_valueSize, defValSize);
        value = array.getString(R.styleable.TitledSeekbar_defaultValue);
        setValue(value);
        setValueSize(valueSize);
    }

    public void setValueSize(int size) {
        if (valueView != null) {
            valueView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    private void setupTitle(TypedArray array) {
        title = array.getString(R.styleable.TitledSeekbar_statTitle);
        titleSize = array.getDimensionPixelSize(R.styleable.TitledSeekbar_titleSize, defTitleSize);
        setTitle(title);
        setTitleSize(titleSize);
    }

    public void setTitle(String title) {
        if (titleView != null) {
            titleView.setText(title);
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

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mode.equals(DisplayMode.DEFINED)) {
            String newValue = getMinValue() + (progress * this.jumpValue) + "";
            setValue(newValue);
        } else {
            String v = stringsCollection.get(progress);
            setValue(v);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (valueChanged != null) {
            valueChanged.onTitledSeekBarChanged(this);
        }
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setValueChanged(onValueChanged valueChanged) {
        this.valueChanged = valueChanged;
    }

    public enum DisplayMode {
        ARRAY, DEFINED;
    }

    public interface onValueChanged {
        void onTitledSeekBarChanged(TitledSeekBar seekBar);
    }
}
