package com.bustiblelemons.cthulhator.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.bustiblelemons.BaseFragment;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.GenderSpinnerAdapter;
import com.bustiblelemons.cthulhator.model.brp.gimagesearch.BRPGimageQuery;
import com.bustiblelemons.cthulhator.model.brp.gimagesearch.GImageSearchGender;
import com.bustiblelemons.google.apis.search.params.GoogleImageSearch;
import com.bustiblelemons.views.TitledSeekBar;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by bhm on 23.07.14.
 */
public class PortraitsSettingsFragment extends BaseFragment implements View.OnClickListener,
                                                                       GenderSpinnerAdapter.
                                                                               GenderSelected,
                                                                       TitledSeekBar.onValueChanged {
    private static final String SHOW_UNFOLDED  = "show_unfolded";
    private static final String SEARCH_OPTIONS = "search_options";
    @InjectView(R.id.action_settings)
    View          settingsButton;
    @InjectView(R.id.gender_spinner)
    Spinner       spinner;
    @InjectView(R.id.year_seekbar)
    TitledSeekBar yearSeekbar;
    private View                      content;
    private GoogleImageSearch.Options searchOptions;
    private GoogleSearchOptsListener  searchOptsListener;
    private GenderSpinnerAdapter      genderAdapter;
    private BRPGimageQuery            brpImageQuery;

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
        content = rootView.findViewById(android.R.id.custom);
        ButterKnife.inject(this, rootView);
        yearSeekbar.setValueChanged(this);
        genderAdapter = new GenderSpinnerAdapter(getActivity(), this);
        spinner.setAdapter(genderAdapter);
        spinner.setOnItemSelectedListener(genderAdapter);
        if (settingsButton != null) {
            settingsButton.setOnClickListener(this);
        }
        brpImageQuery = new BRPGimageQuery();
        brpImageQuery.gender(GImageSearchGender.ANY);
        brpImageQuery.year(yearSeekbar.getValue());
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

    @Override
    public boolean onGenderSelected(GImageSearchGender gender) {
        searchOptions.setQuery(brpImageQuery.gender(gender));
        publishNewOptions(searchOptions);
        return false;
    }

    private void publishNewOptions(GoogleImageSearch.Options searchOptions) {
        if (searchOptsListener != null) {
            searchOptsListener.onGoogleSearchOptionsChanged(searchOptions);
        }
    }

    @Override
    public void onTitledSeekBarChanged(TitledSeekBar seekBar) {
        searchOptions.setQuery(brpImageQuery.year(seekBar.getValue()));
        publishNewOptions(searchOptions);
    }

    public interface GoogleSearchOptsListener {
        boolean onGoogleSearchOptionsChanged(GoogleImageSearch.Options newOptions);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }
}
