package com.bustiblelemons.cthulhator.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.fragments.AbsFragment;
import com.bustiblelemons.fragments.PagerTitle;
import com.bustiblelemons.utils.BundleTools;

/**
 * Created by bhm on 02.08.14.
 */
public abstract class AbsFragmentWithParcelable<A extends Parcelable> extends AbsFragment
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
            instanceArgument = savedInstanceState.getParcelable(NEW_INSTANCE_ARG);
        } else if (hasArgument(NEW_INSTANCE_ARG)) {
            instanceArgument = getArguments().getParcelable(NEW_INSTANCE_ARG);
        }
        onInstanceArgumentRead(instanceArgument);
    }

    protected abstract void onInstanceArgumentRead(A instanceArgument);

    public void updateInstanceArgument(A newInstanceArgument) {
        onInstanceArgumentRead(newInstanceArgument);
    }

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
