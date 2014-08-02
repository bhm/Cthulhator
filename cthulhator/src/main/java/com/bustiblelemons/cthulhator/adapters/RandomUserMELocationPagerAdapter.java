package com.bustiblelemons.cthulhator.adapters;

import android.support.v4.app.FragmentManager;

import com.bustiblelemons.adapters.viewpager.AbsFragmentPagerAdapter;
import com.bustiblelemons.api.random.names.randomuserdotme.model.User;

import java.util.List;

/**
 * Created by bhm on 02.08.14.
 */
public class RandomUserMELocationPagerAdapter extends AbsFragmentPagerAdapter<User, LocaitonFragment> {
    public RandomUserMELocationPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public RandomUserMELocationPagerAdapter(FragmentManager fm, List<User> data) {
        super(fm, data);
    }

    @Override
    public LocaitonFragment newInstance(User item) {
        return LocaitonFragment.newInstance(item.getLocation());
    }
}
