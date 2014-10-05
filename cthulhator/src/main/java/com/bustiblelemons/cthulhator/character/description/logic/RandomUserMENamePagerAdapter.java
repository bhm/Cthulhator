package com.bustiblelemons.cthulhator.character.description.logic;

import android.support.v4.app.FragmentManager;

import com.bustiblelemons.adapters.viewpager.AbsFragmentPagerAdapter;
import com.bustiblelemons.cthulhator.character.description.ui.NameFragment;
import com.bustiblelemons.randomuserdotme.model.User;

import java.util.List;

/**
 * Created by bhm on 02.08.14.
 */
public class RandomUserMENamePagerAdapter extends AbsFragmentPagerAdapter<User, NameFragment> {
    public RandomUserMENamePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public RandomUserMENamePagerAdapter(FragmentManager fm, List<User> data) {
        super(fm, data);
    }

    @Override
    public NameFragment newInstance(User item) {
        return NameFragment.newInstance(item.getName());
    }
}
