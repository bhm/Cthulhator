package com.bustiblelemons.cthulhator.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.BaseFragment;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.google.apis.search.params.GoogleImageSearch;

/**
 * Created by bhm on 23.07.14.
 */
public class PortraitsSettingsFragment extends BaseFragment implements View.OnClickListener {
    private static final String SHOW_UNFOLDED  = "show_unfolded";
    private static final String SEARCH_OPTIONS = "search_options";
    private View                      settingsButton;
    private View                      content;
    private GoogleImageSearch.Options searchOptions;
    private GoogleSearchOptsListener  searchOptsListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (hasArgument(SEARCH_OPTIONS)) {
            searchOptions = (GoogleImageSearch.Options)
                    getArguments().getSerializable(SEARCH_OPTIONS);
        }
        if (activity instanceof GoogleSearchOptsListener) {
            searchOptsListener = (GoogleSearchOptsListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_portraits_settings, container, false);
        settingsButton = rootView.findViewById(R.id.action_settings);
        content = rootView.findViewById(android.R.id.custom);
        if (settingsButton != null) {
            settingsButton.setOnClickListener(this);
        }
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.action_settings:
            boolean expand = !view.isSelected();
            expandSettings(expand);
            view.setSelected(expand);
            break;
        }
    }

    public void expandSettings(boolean expand) {
        if (content != null) {
            content.setVisibility(expand ? View.VISIBLE : View.GONE);
        }
    }

    public static PortraitsSettingsFragment newInstance(GoogleImageSearch.Options searchOptions) {
        PortraitsSettingsFragment r = new PortraitsSettingsFragment();
        Bundle args = new Bundle();
        args.putSerializable(SEARCH_OPTIONS, searchOptions);
        r.setArguments(args);
        return r;
    }

    public interface GoogleSearchOptsListener {
        boolean onGoogleSearchOptionsChanged(GoogleImageSearch.Options newOptions);
    }
}
