package com.bustiblelemons.settings;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.bustiblelemons.bustiblelibs.R;


public class SeekBarPreference extends Preference implements OnSeekBarChangeListener {

	private String namespace = "com.bustiblelemons.seekbarpreference";

	private int minValue = 1;
	private int maxValue = 100;
	private int interval = 1;
	private int currentValue = 0;
	private static final int DEFAULT_VALUE = 50;

	private String xmlMaxValue = "maxValue";
	private String xmlMinValue = "minValue";
	private String xmlInterval = "interval";
	private String xmlUnitsName = "units";
	private String units = "";
	private String xmlValueInSummary = "valueInSummary";
	private boolean valueInSummary = false;

	// Threshold allows to switch to a higher order naming. grams to kg, minutes
	// to hours, etc.
	private int threshold = maxValue;
	private int thresholdDenominator = maxValue;
	private String xmlThreshold = "threshold";
	private String xmlThresholdDenominator = "thresholdDenominator";
	private String xmlThresholdUnits = "thresholdUnits";
	private String thresholdUnits = "";

	private SeekBar seekBar;
	private TextView valueView;

	public SeekBarPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPreference(context, attrs);
	}

	public SeekBarPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPreference(context, attrs);
	}

	private void initPreference(Context context, AttributeSet attrs) {
		setNamespace(context);
		setValues(attrs);
		seekBar = new SeekBar(context, attrs);
		seekBar.setMax(maxValue);
		seekBar.setOnSeekBarChangeListener(this);
	}

	private void setNamespace(Context context) {
		this.namespace = context.getString(R.string.bustiblelemons_xml_namespace);
	}

	private void setValues(AttributeSet attrs) {
		minValue = attrs.getAttributeIntValue(namespace, xmlMinValue, minValue);
		maxValue = attrs.getAttributeIntValue(namespace, xmlMaxValue, maxValue);
		units = getAttributeStringValue(attrs, namespace, xmlUnitsName, units);
		
		threshold = attrs.getAttributeIntValue(namespace, xmlThreshold, threshold);
		thresholdUnits = getAttributeStringValue(attrs, namespace, xmlThresholdUnits, thresholdUnits);
		thresholdDenominator = attrs.getAttributeIntValue(namespace, xmlThresholdDenominator, thresholdDenominator);
		valueInSummary = attrs.getAttributeBooleanValue(namespace, xmlValueInSummary, valueInSummary);
		
		try {
			String attrInterval = attrs.getAttributeValue(namespace, xmlInterval);
			interval = attrInterval != null ? Integer.parseInt(attrInterval) : interval;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	private String getAttributeStringValue(AttributeSet attrs, String namespace, String name, String defaultValue) {
		String value = attrs.getAttributeValue(namespace, name);
		if (value == null)
			value = defaultValue;
		return value;
	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		try {
			ViewParent oldParent = seekBar.getParent();
			ViewGroup newParent = (ViewGroup) view.findViewById(R.id.seekbar_preference_container);
			if (oldParent != newParent) {
				if (oldParent != null) {
					((ViewGroup) oldParent).removeView(seekBar);
				}
				newParent.removeAllViews();
				newParent.addView(seekBar, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected View onCreateView(ViewGroup parent) {
		View view = null;
		try {
			view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seekbar_preference, parent, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		updateView(view);
		return view;
	}

	protected void updateView(View view) {
		try {
			valueView = (TextView) view.findViewById(R.id.seekbar_preference_value);
			valueView.setVisibility(valueInSummary ? View.GONE : View.VISIBLE);
			valueView.setText(String.valueOf(currentValue));
			seekBar.setProgress(currentValue - minValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void changeUnits(int currentValue, int threshold) {
		StringBuilder b = new StringBuilder();
		if (currentValue > threshold) {
			b.append(currentValue / thresholdDenominator).append(" ").append(thresholdUnits).append(" ")
					.append(currentValue % thresholdDenominator).append(" ").append(units);
		} else {
			b.append(currentValue).append(" ").append(units);
		}
		valueView.setText(b.toString());

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		int newValue = progress + minValue;
		if (newValue > maxValue) {
			newValue = maxValue;
		} else if (newValue < minValue) {
			newValue = minValue;
		} else if (newValue != 1 && newValue % interval != 0) {
			newValue = Math.round(((float) newValue) / interval) * interval;
		}
		if (!callChangeListener(newValue)) {
			seekBar.setProgress(currentValue - minValue);
			return;
		}
		currentValue = newValue;
		changeUnits(currentValue, threshold);
		persistInt(currentValue);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		changeUnits(currentValue, threshold);
	}

	@Override
	protected Object onGetDefaultValue(TypedArray a, int index) {
		int defaultValue = a.getInt(index, DEFAULT_VALUE);
		return defaultValue;
	}

	@Override
	protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
		super.onSetInitialValue(restorePersistedValue, defaultValue);
		if (restorePersistedValue) {
			currentValue = getPersistedInt(currentValue);
		} else {
			int t = 0;
			try {
				t = (Integer) defaultValue;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				currentValue = t;
				persistInt(currentValue);
			}
		}
	}

}
