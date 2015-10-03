package com.bustiblelemons.cthulhator.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.fragments.AbsFragment;
import com.bustiblelemons.fragments.PagerTitle;
import com.bustiblelemons.utils.BundleTools;

import java.io.Serializable;

/**
 * Created by bhm on 02.08.14.
 */
public abstract class AbsFragmentWithArg<A> extends AbsFragment
        implements PagerTitle {

    private static final String NEW_INSTANCE_ARG = "new_instance_arg";
    private A mInstanceArgument;

    public A getInstanceArgument() {
        return mInstanceArgument;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        readInstanceArgument(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void readInstanceArgument(Bundle savedInstanceState) {
        if (BundleTools.contains(savedInstanceState, NEW_INSTANCE_ARG)) {
            Object object = savedInstanceState.get(NEW_INSTANCE_ARG);
            if (object instanceof Parcelable) {
                mInstanceArgument = savedInstanceState.getParcelable(NEW_INSTANCE_ARG);
            } else if (object instanceof Serializable) {
                mInstanceArgument = (A) savedInstanceState.getSerializable(NEW_INSTANCE_ARG);
            }
        } else if (hasArgument(NEW_INSTANCE_ARG)) {
            Object object = getArguments().get(NEW_INSTANCE_ARG);
            if (object instanceof Parcelable) {
                mInstanceArgument = getArguments().getParcelable(NEW_INSTANCE_ARG);
            } else if (object instanceof Serializable) {
                mInstanceArgument = (A) getArguments().getSerializable(NEW_INSTANCE_ARG);
            }
        }
        onInstanceArgumentRead(mInstanceArgument);
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
        writeInstanceArg(outState, mInstanceArgument);
    }

    private void writeInstanceArg(Bundle outState, A instanceArgument) {
        if (this.mInstanceArgument instanceof Parcelable) {
            outState.putParcelable(NEW_INSTANCE_ARG, (Parcelable) instanceArgument);
        } else if (this.mInstanceArgument instanceof Serializable) {
            outState.putSerializable(NEW_INSTANCE_ARG, (Serializable) instanceArgument);
        }
    }

    public Bundle getArgumentBundle(A instanceArgument) {
        Bundle r = new Bundle();
        writeInstanceArg(r, instanceArgument);
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
