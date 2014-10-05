package com.bustiblelemons.cthulhator.character.portrait.ui;

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
import com.bustiblelemons.cthulhator.character.history.model.YearsPeriod;
import com.bustiblelemons.cthulhator.character.portrait.logic.OnBroadcastOnlineSearchSettings;
import com.bustiblelemons.cthulhator.character.portrait.model.OnlineSearchUISettings;
import com.bustiblelemons.cthulhator.settings.Settings;
import com.bustiblelemons.cthulhator.settings.character.CharacterSettings;
import com.bustiblelemons.cthulhator.settings.character.CharacterSettingsImpl;
import com.bustiblelemons.fragments.dialog.AbsArgDialogFragment;
import com.bustiblelemons.google.apis.GoogleSearchGender;
import com.bustiblelemons.logging.Logger;
import com.bustiblelemons.views.TitledSeekBar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by bhm on 03.08.14.
 */
public class RandomCharSettingsDialog extends AbsArgDialogFragment<CharacterSettings>
        implements GenderSpinnerAdapter.GenderSelected,
                   PeriodSpinnerAdapter.OnYearsPeriodSelected,
                   View.OnClickListener {

    public static final  String TAG = RandomCharSettingsDialog.class.getSimpleName();
    private static final Logger log = new Logger(RandomCharSettingsDialog.class);
    @InjectView(R.id.gender_spinner)
    Spinner       genderSpinner;
    @InjectView(R.id.year_spinner)
    Spinner       periodSpinner;
    @InjectView(R.id.year_seekbar)
    TitledSeekBar yearSeekbar;

    private GenderSpinnerAdapter            genderAdapter;
    private PeriodSpinnerAdapter            periodSpinnerAdapter;
    private OnBroadcastOnlineSearchSettings mBroadcastCallback;
    private GoogleSearchGender              mGoogleSearchGender;
    private CharacterSettings               characterSettings;
    private OnlineSearchUISettings          onlineSearchUISettings;

    public static RandomCharSettingsDialog newInstance(CharacterSettings settings) {
        RandomCharSettingsDialog r = new RandomCharSettingsDialog();
        r.setNewInstanceArgument(settings);
        return r;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnBroadcastOnlineSearchSettings) {
            mBroadcastCallback = (OnBroadcastOnlineSearchSettings) activity;
        }
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
        return rootView;
    }

    @Override
    protected void onInstanceArgumentRead(CharacterSettings instanceArgument) {
        this.characterSettings = instanceArgument;
        mGoogleSearchGender = characterSettings.getGender();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onlineSearchUISettings = Settings.getLastKnownPhotoSearchUISettings(view.getContext());
        if (genderSpinner != null) {
            setupGenderSpinner(view.getContext());
        }
        if (periodSpinner != null) {
            setupPeriodSpinner(view.getContext());
        }
        if (yearSeekbar != null) {
            yearSeekbar.setProgress(onlineSearchUISettings.getSeekbarPosition());
        }
    }

    public void setupPeriodSpinner(Context context) {
        periodSpinnerAdapter = new PeriodSpinnerAdapter(context, this);
        periodSpinner.setOnItemSelectedListener(periodSpinnerAdapter);
        periodSpinner.setAdapter(periodSpinnerAdapter);
        periodSpinner.setSelection(onlineSearchUISettings.getPeriodSpinnerPosition());
    }

    private void setupGenderSpinner(Context context) {
        genderAdapter = new GenderSpinnerAdapter(context, this);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setOnItemSelectedListener(genderAdapter);
        genderSpinner.setSelection(onlineSearchUISettings.getGenderSpinnerPosition());
    }

    @Override
    public boolean onGenderSelected(GoogleSearchGender googleSearchGender) {
        mGoogleSearchGender = googleSearchGender;
        return false;
    }

    @OnClick({android.R.id.button1, android.R.id.closeButton})
    @Override
    public void onClick(View v) {
        boolean apply = v.getId() == android.R.id.button1;
        if (mBroadcastCallback != null) {
            onlineSearchUISettings = new OnlineSearchUISettings();
            onlineSearchUISettings.setGenderSpinnerPosition(
                    genderSpinner.getSelectedItemPosition());
            onlineSearchUISettings.setPeriodSpinnerPosition(
                    periodSpinner.getSelectedItemPosition());
            onlineSearchUISettings.setSeekbarPosition(yearSeekbar.getProgress());
            int year = yearSeekbar.getIntValue();
            characterSettings = CharacterSettingsImpl.create(year, mGoogleSearchGender);
            if (apply) {
                Settings.saveLastOnlinePhotoSearchQuery(getActivity(), characterSettings);
                Settings.setLastKnownPhotoSearchUISettings(getActivity(), onlineSearchUISettings);
            }
            mBroadcastCallback.onSettingsChanged(characterSettings, apply);
        }
        dismiss();
    }

    @Override
    public void onYearsPeriodSelected(YearsPeriod period) {
        if (yearSeekbar != null) {
            log.d("onYearsPeriodSelected %s", period);
            yearSeekbar.setMinValue(period.getMinYear());
            yearSeekbar.setJumpValue(period.getYearJump());
            yearSeekbar.setMaxValue(period.getMaxYear());
        }
    }
}
