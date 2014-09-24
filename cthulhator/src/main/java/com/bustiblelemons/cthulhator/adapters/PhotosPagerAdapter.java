package com.bustiblelemons.cthulhator.adapters;

import android.support.v4.app.FragmentManager;

import com.bustiblelemons.adapters.viewpager.AbsFragmentPagerAdapter;
import com.bustiblelemons.cthulhator.fragments.RandomUserPhotoFragment;
import com.bustiblelemons.model.OnlinePhotoUrl;

/**
 * Created by bhm on 02.08.14.
 */
public class PhotosPagerAdapter
        extends AbsFragmentPagerAdapter<OnlinePhotoUrl, RandomUserPhotoFragment> {
    public PhotosPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public RandomUserPhotoFragment getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public RandomUserPhotoFragment newInstance(OnlinePhotoUrl item) {
        return RandomUserPhotoFragment.newInstance(item);
    }
}
