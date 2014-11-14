package com.bustiblelemons.fragments.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.bustiblelemons.utils.BundleTools;

import java.io.Serializable;

import butterknife.ButterKnife;

/**
 * Created by bhm on 03.08.14.
 */
public abstract class AbsArgDialogFragment<A extends Serializable> extends DialogFragment {
    private static final String NEW_INSTANCE_ARG = "new_instance_arg";
    private A       instanceArgument;
    private boolean hasTitle;

    public void setHasTitle(boolean hasTitle) {
        this.hasTitle = hasTitle;
    }

    private boolean hasTitle() {
        return hasTitle;
    }

    public A getInstanceArgument() {
        return instanceArgument;
    }

    public Bundle getArgumentBundle(A instanceArgument) {
        Bundle r = new Bundle();
        r.putSerializable(NEW_INSTANCE_ARG, instanceArgument);
        return r;
    }

    public Bundle setNewInstanceArgument(A instanceArgument) {
        Bundle args = getArgumentBundle(instanceArgument);
        setArguments(args);
        return args;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (hasTitle()) {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return dialog;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(NEW_INSTANCE_ARG, instanceArgument);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        readInstanceArgument(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        readInstanceArgument(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        readInstanceArgument(savedInstanceState);
    }

    public void readInstanceArgument(Bundle savedInstanceState) {
        if (BundleTools.contains(savedInstanceState, NEW_INSTANCE_ARG)) {
            instanceArgument = (A) savedInstanceState.getSerializable(NEW_INSTANCE_ARG);
        } else if (hasArgument(NEW_INSTANCE_ARG)) {
            instanceArgument = (A) getArguments().getSerializable(NEW_INSTANCE_ARG);
        }
        onInstanceArgumentRead(instanceArgument);
    }

    protected abstract void onInstanceArgumentRead(A instanceArgument);

    public boolean hasArgument(String key) {
        return hasArguments() && getArguments().containsKey(key);
    }

    public boolean hasArguments() {
        return getArguments() != null;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }
}
