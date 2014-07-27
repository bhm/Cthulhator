package com.bustiblelemons.cthulhator.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.BaseFragment;
import com.bustiblelemons.api.random.names.randomuserdotme.model.Name;
import com.bustiblelemons.api.random.names.randomuserdotme.model.User;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.view.LocationWidget;
import com.bustiblelemons.cthulhator.view.NameWidget;
import com.bustiblelemons.views.LoadingImage;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelperBase;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

import butterknife.ButterKnife;

/**
 * Created by bhm on 26.07.14.
 */
public class RandomUserFragment extends BaseFragment {
    public static final  String USER     = "user";
    private static final String LOCATION = "location";
    private FadingActionBarHelperBase mFadingHelper;

    NameWidget     nameWidget;
    LocationWidget locationWidget;
    LoadingImage   pictureView;
    private User user;

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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(USER, user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = mFadingHelper.createView(getContext());
        nameWidget = (NameWidget) rootView.findViewById(R.id.name);
        locationWidget = (LocationWidget) rootView.findViewById(R.id.location);
        pictureView = (LoadingImage) rootView.findViewById(R.id.image_header);
        if (hasArgument(USER)) {
            user = (User) getArguments().getSerializable(USER);
            fillUserInfo();
        }
        return rootView;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            savedInstanceState.containsKey(USER);
            user = (User) savedInstanceState.getSerializable(USER);
            log.d("onViewStateRestored %s", user.getName().getFullName());
            fillUserInfo();
        }
    }

    public void fillUserInfo() {
        if (locationWidget != null) {
            locationWidget.setLocation(user.getLocation());
        }
        if (nameWidget != null) {
            Name name = user.getName();
            String fullName = name.getFirst() + " " + name.getLast();
            nameWidget.setName(fullName);
            nameWidget.setTitle(name.getTitle());
        }
        if (pictureView != null) {
            String picUrl = user.getPicture();
            pictureView.loadFrom(picUrl);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
