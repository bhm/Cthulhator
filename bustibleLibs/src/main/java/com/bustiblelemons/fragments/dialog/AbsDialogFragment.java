package com.bustiblelemons.fragments.dialog;

import android.support.v4.app.DialogFragment;

import butterknife.ButterKnife;

/**
 * Created by bhm on 03.08.14.
 */
public class AbsDialogFragment extends DialogFragment {

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }
}
