package com.bustiblelemons.cthulhator.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.model.LocationInfo;
import com.micromobs.android.floatlabel.FloatLabelEditText;

/**
 * Created by bhm on 26.07.14.
 */
public class LocationWidget extends RelativeLayout {

    FloatLabelEditText streetInput;
    FloatLabelEditText cityInput;
    FloatLabelEditText zipCodeInput;
    FloatLabelEditText stateInput;
    private View         rootView;
    private LocationInfo mInfo;
    private String       mStreet;
    private String       mState;
    private String       mCity;
    private String       mZipcode;
    private boolean enableMapPicker = true;
    private ImageButton pickLocationButton;
    private Drawable    locationPickerDrawable;

    public LocationWidget(Context context) {
        super(context);
        init(context, null);
    }

    public LocationWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LocationWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public void setZipcode(String zipcode) {
        if (zipCodeInput != null) {
            zipCodeInput.setText(zipcode);
            this.mZipcode = zipcode;
        }
    }

    public String getCity() {
        return mCity == null ? mInfo != null ? mInfo.getCity() : mCity : null;
    }

    public void setCity(String city) {
        if (cityInput != null) {
            cityInput.setText(city);
            this.mCity = city;
        }
    }

    public String getState() {
        return mState == null ? mInfo != null ? mInfo.getState() : mState : null;
    }

    public void setState(String state) {
        if (stateInput != null && stateInput.getEditText() != null) {
            stateInput.setText(state);
            this.mState = state;
        }
    }

    public String getStreet() {
        return mStreet == null ? mInfo != null ? mInfo.getStreet() : mStreet : null;
    }

    public void setStreet(String street) {
        if (streetInput != null) {
            streetInput.setText(street);
            this.mStreet = street;
        }
    }

    public LocationInfo getInfo() {
        return mInfo;
    }

    public void setInfo(LocationInfo mInfo) {
        this.mInfo = mInfo;
    }

    private void init(Context context, AttributeSet attrs) {
        rootView = LayoutInflater.from(context).inflate(R.layout.location_widget, this, true);
        cityInput = (FloatLabelEditText) rootView.findViewById(R.id.city);
        stateInput = (FloatLabelEditText) rootView.findViewById(R.id.state);
        streetInput = (FloatLabelEditText) rootView.findViewById(R.id.street);
        zipCodeInput = (FloatLabelEditText) rootView.findViewById(R.id.zipcode);
        pickLocationButton = (ImageButton) rootView.findViewById(R.id.pick_location);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LocationWidget);
            enableMapPicker = array.getBoolean(R.styleable.LocationWidget_enableMapPicker,
                    enableMapPicker);
            enableMapPicker(enableMapPicker);
            locationPickerDrawable = array.getDrawable(R.styleable.LocationWidget_pickerDrawable);
            if (locationPickerDrawable != null) {
                setLocationPicker(locationPickerDrawable);
            }
        }
    }

    public void setLocationPicker(int resId) {
        if (pickLocationButton != null) {
            pickLocationButton.setImageResource(resId);
        }
    }

    public void setLocationPicker(Drawable drawable) {
        if (pickLocationButton != null) {
            pickLocationButton.setImageDrawable(drawable);
        }
    }

    public void enableMapPicker(boolean enable) {
        if (enable) {
            if (pickLocationButton != null) {
                pickLocationButton.setVisibility(View.VISIBLE);
            }
        } else {
            if (pickLocationButton != null) {
                pickLocationButton.setVisibility(View.GONE);
            }
        }
    }

    public void setLocation(LocationInfo info) {
        this.mInfo = info;
        setStreet(info.getStreet());
        setState(info.getState());
        setCity(info.getCity());
        setZipcode(info.getZipCode());
    }

    public String getZipCode() {
        return mZipcode != null ? mZipcode : mInfo != null ? mInfo.getZipCode() : null;
    }
}
