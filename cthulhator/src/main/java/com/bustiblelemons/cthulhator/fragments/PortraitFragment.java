package com.bustiblelemons.cthulhator.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.fragments.AbsFragment;
import com.bustiblelemons.google.apis.model.GoogleImageObject;
import com.bustiblelemons.views.LoadingImage;

/**
 * Created by bhm on 18.07.14.
 */
public class PortraitFragment extends AbsFragment {
    public static final String URL = "url";
    private LoadingImage image;

    public static PortraitFragment newInstance(GoogleImageObject imageObject) {
        PortraitFragment r = new PortraitFragment();
        Bundle args = new Bundle();
        args.putString(URL, imageObject.getUrl());
        r.setArguments(args);
        return r;
    }

    public static PortraitFragment newInstance() {
        return new PortraitFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_portraits, container, false);
        image = (LoadingImage) rootView.findViewById(android.R.id.icon);
        if (hasArgument(URL)) {
            String url = getArguments().getString(URL);
            log.d("url %s", url);
            image.loadFrom(url);
        }
        return rootView;
    }
}
