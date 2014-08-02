package com.bustiblelemons.cthulhator.adapters;

import android.support.v4.app.FragmentManager;

import com.bustiblelemons.adapters.viewpager.AbsFragmentPagerAdapter;
import com.bustiblelemons.api.random.names.randomuserdotme.model.User;
import com.bustiblelemons.cthulhator.fragments.RandomUserPhotoFragment;

/**
 * Created by bhm on 02.08.14.
 */
public class RandomUserMEPhotoPagerAdapter
        extends AbsFragmentPagerAdapter<User, RandomUserPhotoFragment>{
    public RandomUserMEPhotoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public RandomUserPhotoFragment newInstance(User user) {
        return RandomUserPhotoFragment.newInstance(user);
    }
}
