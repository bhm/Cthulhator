package com.bustiblelemons.cthulhator.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.GenderSpinnerAdapter;
import com.bustiblelemons.cthulhator.model.CharacterSettings;
import com.bustiblelemons.cthulhator.model.CharacterSettingsImpl;
import com.bustiblelemons.cthulhator.model.brp.gimagesearch.BRPGimageQuery;
import com.bustiblelemons.google.apis.GoogleSearchGender;
import com.bustiblelemons.google.apis.search.params.GoogleImageSearch;
import com.bustiblelemons.views.TitledSeekBar;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by bhm on 23.07.14.
 */
public class PortraitsSettingsFragment extends AbsArgFragment<GoogleImageSearch.Options>
        implements View.OnClickListener,
                   GenderSpinnerAdapter.GenderSelected,
                   TitledSeekBar.onValueChanged {

    @InjectView(R.id.action_settings)
    View          settingsButton;
    @InjectView(R.id.gender_spinner)
    Spinner       spinner;
    @InjectView(R.id.year_seekbar)
    TitledSeekBar yearSeekbar;
    @InjectView(android.R.id.custom)
    View          content;

    private GoogleImageSearch.Options       searchOptions;
    private GoogleSearchOptsListener        searchOptsListener;
    private GenderSpinnerAdapter            genderAdapter;
    private BRPGimageQuery                  brpImageQuery;
    private OnOpenSearchSettings            onOpenSearchSettings;
    private OnBroadcastOnlineSearchSettings onBroadcastOnlineSearchSettings;
    private CharacterSettings               onlinePhotoSearchQuery;
    private GoogleSearchGender mGoogleSearchGender = GoogleSearchGender.ANY;
    private boolean            MFoldedOnly         = false;

    public static PortraitsSettingsFragment newInstance(GoogleImageSearch.Options searchOptions) {
        PortraitsSettingsFragment r = new PortraitsSettingsFragment();
        r.setNewInstanceArgument(searchOptions);
        return r;
    }

    public void setOnBroadcastOnlineSearchSettings(OnBroadcastOnlineSearchSettings onBroadcastOnlineSearchSettings) {
        this.onBroadcastOnlineSearchSettings = onBroadcastOnlineSearchSettings;
    }

    public void setFoldedOnly(boolean foldedOnly) {
        this.MFoldedOnly = foldedOnly;
        if (content != null) {
            content.setVisibility(foldedOnly ? View.GONE : View.VISIBLE);
        }
    }

    public void setOnOpenSearchSettings(OnOpenSearchSettings onOpenSearchSettings) {
        this.onOpenSearchSettings = onOpenSearchSettings;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        readLastSettings();
        if (activity instanceof GoogleSearchOptsListener) {
            searchOptsListener = (GoogleSearchOptsListener) activity;
        }
        if (activity instanceof OnBroadcastOnlineSearchSettings) {
            onBroadcastOnlineSearchSettings = (OnBroadcastOnlineSearchSettings) activity;
            onBroadcastOnlineSearchSettings.onSettingsChanged(onlinePhotoSearchQuery,
                    true);
        }
    }

    public void readLastSettings() {
        onlinePhotoSearchQuery = com.bustiblelemons.cthulhator.settings.Settings.getLastPortratiSettings(
                getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_portraits_settings, container, false);
        ButterKnife.inject(this, rootView);
        yearSeekbar.setValueChangedCallback(this);
        setupGenderSpinner();
        if (settingsButton != null) {
            settingsButton.setOnClickListener(this);
        }
        brpImageQuery = new BRPGimageQuery();
        brpImageQuery.gender(GoogleSearchGender.ANY);
        brpImageQuery.year(yearSeekbar.getValue());
        return rootView;
    }

    @Override
    protected void onInstanceArgumentRead(GoogleImageSearch.Options arg) {
        searchOptions = arg;
    }

    private void setupGenderSpinner() {
        genderAdapter = new GenderSpinnerAdapter(getActivity(), this);
        spinner.setAdapter(genderAdapter);
        spinner.setOnItemSelectedListener(genderAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_settings:
                handleSettingsButton(view);
                break;
        }
    }

    public void handleSettingsButton(View view) {
        if (!MFoldedOnly) {
            boolean expand = !view.isSelected();
            expandSettings(expand);
            view.setSelected(expand);
        }
        if (onOpenSearchSettings != null) {
            int year = yearSeekbar.getIntValue();
            CharacterSettings q = CharacterSettingsImpl.create(year, mGoogleSearchGender);
            onOpenSearchSettings.onOpenSettings(q);
        }
    }

    public void expandSettings(boolean expand) {
        if (content != null) {
            content.setVisibility(expand ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public boolean onGenderSelected(GoogleSearchGender googleSearchGender) {
        if (searchOptions != null) {
            mGoogleSearchGender = googleSearchGender;
            searchOptions.setQuery(brpImageQuery.gender(googleSearchGender));
            publishNewOptions(searchOptions);
        }
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

}
