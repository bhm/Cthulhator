package com.bustiblelemons.cthulhator.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.BaseFragment;
import com.bustiblelemons.cthulhator.R;

/**
 * Created by bhm on 18.07.14.
 */
public class PortraitFragment extends BaseFragment {
    public static final String URL = "url";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_portraits, container, false);
        return rootView;
    }

    public static PortraitFragment newInstance(String url) {
        PortraitFragment r = new PortraitFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        r.setArguments(args);
        return r;
    }
}
