package com.bustiblelemons.cthulhator.adapters;

import android.support.v4.app.FragmentManager;

import com.bustiblelemons.adapters.viewpager.AbsFragmentPagerAdapter;
import com.bustiblelemons.cthulhator.fragments.PeriodFragment;
import com.bustiblelemons.cthulhator.model.time.CthulhuPeriod;
import com.bustiblelemons.cthulhator.model.time.YearsPeriod;

/**
 * Created by bhm on 03.08.14.
 */
public class PeriodPagerAdapter extends AbsFragmentPagerAdapter<YearsPeriod, PeriodFragment> {

    public PeriodPagerAdapter(FragmentManager fm) {
        super(fm);
        addData(CthulhuPeriod.values());
    }

    @Override
    public PeriodFragment newInstance(YearsPeriod item) {
        return PeriodFragment.newInstance(item);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getItem(position).getPageTitle();
    }
}