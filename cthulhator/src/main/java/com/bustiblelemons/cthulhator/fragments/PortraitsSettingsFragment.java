package com.bustiblelemons.cthulhator.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.GenderSpinnerAdapter;
import com.bustiblelemons.cthulhator.adapters.PeriodSpinnerAdapter;
import com.bustiblelemons.cthulhator.fragments.dialog.OnlineSearchUISettings;
import com.bustiblelemons.cthulhator.model.CharacterSettings;
import com.bustiblelemons.cthulhator.model.CharacterSettingsImpl;
import com.bustiblelemons.cthulhator.model.time.YearsPeriod;
import com.bustiblelemons.cthulhator.settings.Settings;
import com.bustiblelemons.google.apis.GoogleSearchGender;
import com.bustiblelemons.google.apis.search.params.GoogleImageSearch;
import com.bustiblelemons.views.TitledSeekBar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by bhm on 23.07.14.
 */
public class PortraitsSettingsFragment extends AbsArgFragment<CharacterSettings>
        implements View.OnClickListener,
                   GenderSpinnerAdapter.GenderSelected,
                   TitledSeekBar.onValueChanged,
                   PeriodSpinnerAdapter.OnYearsPeriodSelected {

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
    @InjectView(android.R.id.title)
    TextView      titleView;

    private Context mContext;

    private boolean MFoldedOnly = false;
    private GoogleSearchOptsListener        mSearchOptionsChanged;
    private OnBroadcastOnlineSearchSettings mBroadcastSearchSettings;
    private GenderSpinnerAdapter            genderAdapter;
    private PeriodSpinnerAdapter            periodSpinnerAdapter;
    private GoogleImageSearch.Options       searchOptions;
    private OnOpenSearchSettings            onOpenSearchSettings;
    private CharacterSettings               mCharacterSettings;
    private OnlineSearchUISettings          mOnlineSearchUISettings;
    private GoogleSearchGender              mGoogleSearchGender;
    private int                             mSelectedYear;

    public static PortraitsSettingsFragment newInstance(CharacterSettings searchOptions) {
        PortraitsSettingsFragment r = new PortraitsSettingsFragment();
        r.setNewInstanceArgument(searchOptions);
        return r;
    }

    public void setOnBroadcastOnlineSearchSettings(OnBroadcastOnlineSearchSettings onBroadcastOnlineSearchSettings) {
        this.mBroadcastSearchSettings = onBroadcastOnlineSearchSettings;
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
            mSearchOptionsChanged = (GoogleSearchOptsListener) activity;
        }
        if (activity instanceof OnBroadcastOnlineSearchSettings) {
            mBroadcastSearchSettings = (OnBroadcastOnlineSearchSettings) activity;
        }
    }

    public void readLastSettings() {
        mCharacterSettings = Settings.getLastPortratiSettings(getActivity());
        if (mCharacterSettings != null) {
            mSelectedYear = mCharacterSettings.getYear();
            mGoogleSearchGender = mCharacterSettings.getGender();
            mOnlineSearchUISettings = OnlineSearchUISettings.from(mCharacterSettings);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_portraits_settings, container, false);
        ButterKnife.inject(this, rootView);
        yearSeekbar.setValueChangedCallback(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mOnlineSearchUISettings = Settings.getLastKnownPhotoSearchUISettings(view.getContext());
        mContext = view.getContext();
        setupViewForSettings();
    }

    private void setupViewForSettings() {
        if (genderSpinner != null) {
            setupGenderSpinner();
        }
        if (periodSpinner != null) {
            setupPeriodSpinner();
        }
        if (yearSeekbar != null) {
            int progress = mOnlineSearchUISettings.getSeekbarPosition();
            yearSeekbar.setProgress(progress);
        }
        setTitle();
    }

    public void setupPeriodSpinner() {
        periodSpinnerAdapter = new PeriodSpinnerAdapter(mContext, this);
        periodSpinner.setOnItemSelectedListener(periodSpinnerAdapter);
        periodSpinner.setAdapter(periodSpinnerAdapter);
        periodSpinner.setSelection(mOnlineSearchUISettings.getPeriodSpinnerPosition());
    }

    @Override
    protected void onInstanceArgumentRead(CharacterSettings settings) {
        mCharacterSettings = settings;
        mOnlineSearchUISettings = OnlineSearchUISettings.from(settings);
        setupViewForSettings();

    }

    private void setupGenderSpinner() {
        genderAdapter = new GenderSpinnerAdapter(getActivity(), this);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setOnItemSelectedListener(genderAdapter);
    }

    @OnClick(R.id.action_settings)
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
            if (!expand && mBroadcastSearchSettings != null) {
                mBroadcastSearchSettings.onSettingsChanged(mCharacterSettings, true);
            }
        } else {
            if (onOpenSearchSettings != null) {
                int year = yearSeekbar.getIntValue();
                mCharacterSettings = CharacterSettingsImpl.create(year, mGoogleSearchGender);
                onOpenSearchSettings.onOpenSettings(mCharacterSettings);
            }
        }
    }

    public void expandSettings(boolean expand) {
        if (content != null) {
            content.setVisibility(expand ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public boolean onGenderSelected(GoogleSearchGender selectedGender) {
        if (searchOptions != null) {
            mGoogleSearchGender = selectedGender;
            mCharacterSettings = CharacterSettingsImpl.create(mSelectedYear, mGoogleSearchGender);
            searchOptions.setQuery(mCharacterSettings.getQuery());
            publishNewOptions(searchOptions);
        }
        return false;
    }

    private void publishNewOptions(GoogleImageSearch.Options searchOptions) {
        if (mSearchOptionsChanged != null) {
            mSearchOptionsChanged.onGoogleSearchOptionsChanged(searchOptions);
        }
        if (mBroadcastSearchSettings != null) {
            mBroadcastSearchSettings.onSettingsChanged(mCharacterSettings, true);
        }
        setTitle();
    }

    private void setTitle() {
        if (titleView != null && mCharacterSettings != null) {
            titleView.setText(mCharacterSettings.toString());
        }
    }

    @Override
    public void onTitledSeekBarChanged(TitledSeekBar seekBar) {
        mSelectedYear = seekBar.getIntValue();
        mCharacterSettings = CharacterSettingsImpl.create(mSelectedYear, mGoogleSearchGender);
        searchOptions.setQuery(mCharacterSettings.getQuery());
        publishNewOptions(searchOptions);
    }

    @Override
    public void onYearsPeriodSelected(YearsPeriod period) {
        if (yearSeekbar != null) {
            yearSeekbar.setMinValue(period.getMinYear());
            yearSeekbar.setJumpValue(period.getYearJump());
            yearSeekbar.setMaxValue(period.getMaxYear());
        }
    }

    public interface GoogleSearchOptsListener {
        boolean onGoogleSearchOptionsChanged(GoogleImageSearch.Options newOptions);
    }

}
