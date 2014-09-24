package com.bustiblelemons.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.utils.BundleTools;

/**
 * Created by bhm on 24.09.14.
 */
public abstract class AbsParcelableArgFragment<A extends Parcelable>
        extends AbsFragment
        implements PagerTitle {

    private static final String NEW_INSTANCE_ARG = "new_instance_arg";
    private A instanceArgument;

    public A getInstanceArgument() {
        return instanceArgument;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        readInstanceArgument(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void readInstanceArgument(Bundle savedInstanceState) {
        if (BundleTools.contains(savedInstanceState, NEW_INSTANCE_ARG)) {
            instanceArgument = (A) savedInstanceState.getParcelable(NEW_INSTANCE_ARG);
        } else if (hasArgument(NEW_INSTANCE_ARG)) {
            instanceArgument = (A) getArguments().getParcelable(NEW_INSTANCE_ARG);
        }
        onInstanceArgumentRead(instanceArgument);
    }

    protected abstract void onInstanceArgumentRead(A instanceArgument);

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        readInstanceArgument(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(NEW_INSTANCE_ARG, instanceArgument);
    }

    public Bundle getArgumentBundle(A instanceArgument) {
        Bundle r = new Bundle();
        r.putParcelable(NEW_INSTANCE_ARG, instanceArgument);
        return r;
    }

    public Bundle setNewInstanceArgument(A instanceArgument) {
        Bundle args = getArgumentBundle(instanceArgument);
        setArguments(args);
        return args;
    }

    @Override
    public String getPageTitle() {
        return toString();
    }
}
