package com.bustiblelemons.cthulhator.fragments.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Spinner;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.adapters.GenderSpinnerAdapter;
import com.bustiblelemons.cthulhator.adapters.PeriodPagerAdapter;
import com.bustiblelemons.cthulhator.fragments.OnCloseSearchSettings;
import com.bustiblelemons.cthulhator.model.OnlinePhotoSearchQuery;
import com.bustiblelemons.cthulhator.model.OnlinePhotoSearchQueryImpl;
import com.bustiblelemons.cthulhator.model.brp.gimagesearch.Gender;
import com.bustiblelemons.fragments.dialog.AbsDialogFragment;
import com.bustiblelemons.views.TitledSeekBar;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by bhm on 03.08.14.
 */
public class RandomCharSettingsDialog extends AbsDialogFragment
        implements GenderSpinnerAdapter.GenderSelected {

    public static final String TAG = RandomCharSettingsDialog.class.getSimpleName();
    @InjectView(R.id.gender_spinner)
    Spinner   genderSpinner;
    @InjectView(R.id.pager)
    ViewPager pager;
    private GenderSpinnerAdapter  genderAdapter;
    private PeriodPagerAdapter    periodPagerAdapter;
    private OnCloseSearchSettings onCloseSearchSettings;
    private TitledSeekBar         yearSeekbar;
    private Gender                mGender;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnCloseSearchSettings) {
            onCloseSearchSettings = (OnCloseSearchSettings) activity;
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
        if (genderSpinner != null) {
            setupGenderSpinner(inflater.getContext());
        }
        if (pager != null) {
            periodPagerAdapter = new PeriodPagerAdapter(getChildFragmentManager());
            pager.setAdapter(periodPagerAdapter);
        }
        return rootView;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onCloseSearchSettings != null) {
            int year = yearSeekbar.getIntValue();
            OnlinePhotoSearchQuery search = OnlinePhotoSearchQueryImpl.create(year, mGender);
            onCloseSearchSettings.onCloseSearchSettings(search);
        }
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

}
