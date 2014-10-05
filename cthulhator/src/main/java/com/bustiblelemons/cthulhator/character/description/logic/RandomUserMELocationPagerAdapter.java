package com.bustiblelemons.cthulhator.character.description.logic;

import android.support.v4.app.FragmentManager;

import com.bustiblelemons.adapters.viewpager.AbsFragmentPagerAdapter;
import com.bustiblelemons.cthulhator.character.location.ui.LocaitonFragment;
import com.bustiblelemons.randomuserdotme.model.User;

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
