package com.bustiblelemons.cthulhator.fragments.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Spinner;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.GenderSpinnerAdapter;
import com.bustiblelemons.cthulhator.adapters.PeriodSpinnerAdapter;
import com.bustiblelemons.cthulhator.fragments.OnBroadcastOnlineSearchSettings;
import com.bustiblelemons.cthulhator.model.OnlinePhotoSearchQuery;
import com.bustiblelemons.cthulhator.model.OnlinePhotoSearchQueryImpl;
import com.bustiblelemons.cthulhator.model.brp.gimagesearch.Gender;
import com.bustiblelemons.cthulhator.model.time.YearsPeriod;
import com.bustiblelemons.cthulhator.settings.Settings;
import com.bustiblelemons.fragments.dialog.AbsDialogFragment;
import com.bustiblelemons.views.TitledSeekBar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by bhm on 03.08.14.
 */
public class RandomCharSettingsDialog extends AbsDialogFragment
        implements GenderSpinnerAdapter.GenderSelected,
                   PeriodSpinnerAdapter.OnYearsPeriodSelected, View.OnClickListener {

    public static final String TAG = RandomCharSettingsDialog.class.getSimpleName();
    @InjectView(R.id.gender_spinner)
    Spinner       genderSpinner;
    @InjectView(R.id.year_spinner)
    Spinner       yearSpinner;
    @InjectView(R.id.year_seekbar)
    TitledSeekBar yearSeekbar;


    private GenderSpinnerAdapter            genderAdapter;
    private PeriodSpinnerAdapter            periodSpinnerAdapter;
    private OnBroadcastOnlineSearchSettings onBroadcastOnlineSearchSettings;
    private Gender                          mGender;
    private OnlinePhotoSearchQuery          onlinePhotoSearchQuery;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnBroadcastOnlineSearchSettings) {
            onBroadcastOnlineSearchSettings = (OnBroadcastOnlineSearchSettings) activity;
        }
        readLastSettings();
    }

    public void readLastSettings() {
        onlinePhotoSearchQuery = Settings.getLastPortratiSettings(getActivity());
        mGender = onlinePhotoSearchQuery.getGender();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_character_settings, container, false);
        ButterKnife.inject(this, rootView);
        if (genderSpinner != null) {
            setupGenderSpinner(inflater.getContext());
        }
        if (yearSpinner != null) {
            periodSpinnerAdapter = new PeriodSpinnerAdapter(getActivity(), this);
            yearSpinner.setOnItemSelectedListener(periodSpinnerAdapter);
            yearSpinner.setAdapter(periodSpinnerAdapter);
        }
        return rootView;
    }


    private void setupGenderSpinner(Context context) {
        genderAdapter = new GenderSpinnerAdapter(context, this);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setOnItemSelectedListener(genderAdapter);
    }

    @Override
    public boolean onGenderSelected(Gender gender) {
        return false;
    }

    public static RandomCharSettingsDialog newInstance() {
        RandomCharSettingsDialog r = new RandomCharSettingsDialog();
        return r;
    }

    @OnClick({android.R.id.button1, android.R.id.closeButton})
    @Override
    public void onClick(View v) {
        boolean apply = v.getId() == android.R.id.button1;
        if (onBroadcastOnlineSearchSettings != null) {
            int year = yearSeekbar.getIntValue();
            onlinePhotoSearchQuery = OnlinePhotoSearchQueryImpl.create(year, mGender);
            if (apply) {
                Settings.saveLastOnlinePhotoSearchQuery(getActivity(), onlinePhotoSearchQuery);
            }
            onBroadcastOnlineSearchSettings.onBroadcastOnlineSearchSettings(onlinePhotoSearchQuery,
                    apply);
        }
        dismiss();
    }

    @Override
    public void onYearsPeriodSelected(YearsPeriod period) {
        if (yearSeekbar != null) {
            yearSeekbar.setMinValue(period.getMinYear());
            yearSeekbar.setMaxValue(period.getMaxYear());
            yearSeekbar.setJumpValue(period.getYearJump());
        }
    }
}
