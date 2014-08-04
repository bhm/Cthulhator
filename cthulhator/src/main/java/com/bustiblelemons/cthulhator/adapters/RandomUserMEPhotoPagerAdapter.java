package com.bustiblelemons.cthulhator.adapters;

import android.support.v4.app.FragmentManager;

import com.bustiblelemons.adapters.viewpager.AbsFragmentPagerAdapter;
import com.bustiblelemons.cthulhator.fragments.RandomUserPhotoFragment;
import com.bustiblelemons.model.OnlinePhotoUrl;

/**
 * Created by bhm on 02.08.14.
 */
public class  RandomUserMEPhotoPagerAdapter<T extends OnlinePhotoUrl>
        extends AbsFragmentPagerAdapter<T, RandomUserPhotoFragment>{
    public RandomUserMEPhotoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public RandomUserPhotoFragment newInstance(OnlinePhotoUrl item) {
        return RandomUserPhotoFragment.newInstance(item);
    }
}
