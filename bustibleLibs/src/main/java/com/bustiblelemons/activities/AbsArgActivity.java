package com.bustiblelemons.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

/**
 * Created by bhm on 22.09.14.
 */
public abstract class AbsArgActivity<T extends Parcelable> extends AbsActionBarActivity {


    public static final String INSTANCE_ARGUMENT = "instance_argument";

    public static final <T extends Parcelable> Bundle getArguments(T arg) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_ARGUMENT, arg);
        return bundle;
    }

    public T getInstanceArgument() {
        return (T) getExtras().getParcelable(INSTANCE_ARGUMENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_ARGUMENT)) {
            T arg = (T) savedInstanceState.getSerializable(INSTANCE_ARGUMENT);
            onInstanceArgumentRead(arg);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(INSTANCE_ARGUMENT, getInstanceArgument());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        T arg = (T) savedInstanceState.getParcelable(INSTANCE_ARGUMENT);
        onInstanceArgumentRead(arg);
    }

    protected abstract void onInstanceArgumentRead(T arg);

    public void setResult(int code, T passBackdata) {
        Intent data = new Intent();
        data.putExtra(INSTANCE_ARGUMENT, passBackdata);
        setResult(code, data);
    }
}
