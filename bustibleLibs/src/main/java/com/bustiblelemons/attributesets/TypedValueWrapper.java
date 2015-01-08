package com.bustiblelemons.attributesets;

import android.util.TypedValue;

/**
 * Created by hiv on 08.01.15.
 */
public class TypedValueWrapper extends TypedValue {

    public TypedValueWrapper(TypedValue value) {
        this.type  = value.type;
        this.assetCookie = value.assetCookie;
        this.changingConfigurations = value.changingConfigurations;
    }

    public boolean isString() {
        return this.type == TYPE_STRING;
    }

    public boolean isInt() {
        return this.type == TYPE_INT_DEC;
    }


}
