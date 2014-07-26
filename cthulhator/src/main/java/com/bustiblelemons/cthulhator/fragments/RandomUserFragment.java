package com.bustiblelemons.cthulhator.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bustiblelemons.BaseFragment;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.view.LocationWidget;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelperBase;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

import butterknife.InjectView;

/**
 * Created by bhm on 26.07.14.
 */
public class RandomUserFragment extends BaseFragment {
    public static final String USER = "user";
    private FadingActionBarHelperBase mFadingHelper;

    @InjectView(R.id.title)
    TextView       title;
    @InjectView(R.id.name)
    TextView       name;
    @InjectView(R.id.location)
    LocationWidget locationView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFadingHelper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.actionbar_brp)
                .headerLayout(R.layout.header_brp_header_background)
                .headerOverlayLayout(R.layout.fragment_random_user_header)
                .contentLayout(R.layout.fragment_random_user)
                .lightActionBar(false);
        mFadingHelper.initActionBar(activity);
        setActionBarCloseIcon();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = mFadingHelper.createView(getContext());
        return rootView;
    }
}
