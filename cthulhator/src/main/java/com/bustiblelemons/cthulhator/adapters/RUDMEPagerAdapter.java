package com.bustiblelemons.cthulhator.adapters;

import android.support.v4.app.FragmentManager;

import com.bustiblelemons.adapters.viewpager.AbsFragmentPagerAdapter;
import com.bustiblelemons.api.random.names.randomuserdotme.model.User;
import com.bustiblelemons.cthulhator.fragments.RandomUserFragment;

import java.util.List;

/**
 * Created by bhm on 26.07.14.
 */
public class RUDMEPagerAdapter extends AbsFragmentPagerAdapter<User, RandomUserFragment> {

    public RUDMEPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public RUDMEPagerAdapter(FragmentManager fm, List<User> data) {
        super(fm, data);
    }

    @Override
    public RandomUserFragment newInstance(User user) {
        return RandomUserFragment.newInstane(user);
    }
}