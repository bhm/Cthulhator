package com.bustiblelemons.cthulhator.adapters;

import android.support.v4.app.FragmentManager;

import com.bustiblelemons.adapters.viewpager.AbsFragmentPagerAdapter;
import com.bustiblelemons.cthulhator.fragments.PortraitFragment;
import com.bustiblelemons.model.OnlinePhotoUrl;

/**
 * Created by bhm on 18.07.14.
 */
public class PortraitsPagerAdapter
        extends AbsFragmentPagerAdapter<OnlinePhotoUrl, PortraitFragment> {

    public PortraitsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public PortraitFragment newInstance(OnlinePhotoUrl item) {
        return PortraitFragment.newInstance(item);
    }

}
