package com.bustiblelemons.cthulhator.adapters;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.bustiblelemons.adapters.viewpager.AbsFragmentPagerAdapter;
import com.bustiblelemons.api.random.names.randomuserdotme.model.User;
import com.bustiblelemons.cthulhator.fragments.RandomUserFragment;

import java.util.List;

/**
 * Created by bhm on 26.07.14.
 */
public class RandomUserDotMePagerAdapter
        extends AbsFragmentPagerAdapter<User, RandomUserFragment> {

    public RandomUserDotMePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public RandomUserDotMePagerAdapter(FragmentManager fm, List<User> data) {
        super(fm, data);
    }

    @Override
    public RandomUserFragment newInstance(User item) {
        RandomUserFragment r = new RandomUserFragment();
        Bundle args = new Bundle();
        args.putSerializable(RandomUserFragment.USER, item);
        r.setArguments(args);
        return r;
    }
}
