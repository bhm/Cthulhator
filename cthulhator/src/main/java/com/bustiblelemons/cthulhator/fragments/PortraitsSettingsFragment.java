package com.bustiblelemons.cthulhator.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.GenderSpinnerAdapter;
import com.bustiblelemons.cthulhator.adapters.PeriodSpinnerAdapter;
import com.bustiblelemons.cthulhator.fragments.dialog.RandomCharSettings;
import com.bustiblelemons.cthulhator.model.CharacterSettings;
import com.bustiblelemons.cthulhator.model.CharacterSettingsImpl;
import com.bustiblelemons.cthulhator.model.brp.gimagesearch.BRPGimageQuery;
import com.bustiblelemons.cthulhator.model.time.YearsPeriod;
import com.bustiblelemons.cthulhator.settings.Settings;
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
                   TitledSeekBar.onValueChanged, PeriodSpinnerAdapter.OnYearsPeriodSelected {

    @InjectView(R.id.action_settings)
    View          settingsButton;
    @InjectView(R.id.gender_spinner)
    Spinner       genderSpinner;
    @InjectView(R.id.year_spinner)
    Spinner       periodSpinner;
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
    private CharacterSettings               characterSettings;
    private RandomCharSettings              randomCharSettings;
    private GoogleSearchGender mGoogleSearchGender = GoogleSearchGender.ANY;
    private boolean            MFoldedOnly         = false;
    private PeriodSpinnerAdapter periodSpinnerAdapter;

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
            onBroadcastOnlineSearchSettings.onSettingsChanged(characterSettings,
                    true);
        }
    }

    public void readLastSettings() {
        characterSettings = Settings.getLastPortratiSettings(getActivity());
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
        brpImageQuery.gender(GoogleSearchGender.FEMALE);
        brpImageQuery.year(yearSeekbar.getValue());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        randomCharSettings = Settings.getLastRandomCharSettings(view.getContext());
        if (genderSpinner != null) {
            setupGenderSpinner();
        }
        if (periodSpinner != null) {
            setupPeriodSpinner(view.getContext());
        }
        if (yearSeekbar != null) {
            yearSeekbar.setProgress(randomCharSettings.getSeekbarPosition());
        }
    }

    public void setupPeriodSpinner(Context context) {
        periodSpinnerAdapter = new PeriodSpinnerAdapter(context, this);
        periodSpinner.setOnItemSelectedListener(periodSpinnerAdapter);
        periodSpinner.setAdapter(periodSpinnerAdapter);
        periodSpinner.setSelection(randomCharSettings.getPeriodSpinnerPosition());
    }

    @Override
    protected void onInstanceArgumentRead(GoogleImageSearch.Options arg) {
        searchOptions = arg;
    }

    private void setupGenderSpinner() {
        genderAdapter = new GenderSpinnerAdapter(getActivity(), this);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setOnItemSelectedListener(genderAdapter);
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

    @Override
    public void onYearsPeriodSelected(YearsPeriod period) {

    }

    public interface GoogleSearchOptsListener {
        boolean onGoogleSearchOptionsChanged(GoogleImageSearch.Options newOptions);
    }

}
