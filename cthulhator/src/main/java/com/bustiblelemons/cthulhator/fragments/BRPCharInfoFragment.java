package com.bustiblelemons.cthulhator.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.fragments.AbsFragment;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelperBase;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

/**
 * Created by bhm on 25.07.14.
 */
public class BRPCharInfoFragment extends AbsFragment {

    private FadingActionBarHelperBase mFadingHelper;

    public static BRPCharInfoFragment newInstance() {
        BRPCharInfoFragment r = new BRPCharInfoFragment();
        Bundle args = new Bundle();
        r.setArguments(args);
        return r;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFadingHelper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.actionbar_brp)
                .headerLayout(R.layout.header_brp_header_background)
                .headerOverlayLayout(R.layout.fragment_brp_header_overlay)
                .contentLayout(R.layout.fragment_brp_info)
                .lightActionBar(false);
        mFadingHelper.initActionBar(activity);
        setActionBarCloseIcon();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_brp_info, container, false);
        return rootView;
    }
}
