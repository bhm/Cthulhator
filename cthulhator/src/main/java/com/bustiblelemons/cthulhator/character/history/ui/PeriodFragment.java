package com.bustiblelemons.cthulhator.character.history.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.history.model.YearsPeriod;
import com.bustiblelemons.cthulhator.fragments.AbsFragmentWithSerializable;
import com.bustiblelemons.views.TitledSeekBar;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by bhm on 03.08.14.
 */
public class PeriodFragment extends AbsFragmentWithSerializable<YearsPeriod> {

    @InjectView(R.id.year_seekbar)
    TitledSeekBar yearSeekbar;
    private YearsPeriod mPeriod;

    public static PeriodFragment newInstance(YearsPeriod item) {
        PeriodFragment r = new PeriodFragment();
        r.setNewInstanceArgument(item);
        return r;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_period_seekbar, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    protected void onInstanceArgumentRead(YearsPeriod yearsPeriod) {
        loadPeriodInfo(yearsPeriod);
    }

    private void loadPeriodInfo(YearsPeriod period) {
        if (yearSeekbar != null && period != null) {
            mPeriod = period;
            yearSeekbar.setMaxValue(period.getMaxYear());
            yearSeekbar.setMinValue(period.getMinYear());
            yearSeekbar.setJumpValue(period.getYearJump());

        }
    }

    @Override
    public String getPageTitle() {
        return mPeriod != null ? mPeriod.getName() : super.getPageTitle();
    }
}
