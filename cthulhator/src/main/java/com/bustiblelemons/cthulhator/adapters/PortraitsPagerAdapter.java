package com.bustiblelemons.cthulhator.adapters;

import android.support.v4.app.FragmentManager;

import com.bustiblelemons.adapters.viewpager.BaseFragmentPagerAdapter;
import com.bustiblelemons.cthulhator.fragments.PortraitFragment;
import com.bustiblelemons.google.apis.model.GoogleImageObject;

/**
 * Created by bhm on 18.07.14.
 */
public class PortraitsPagerAdapter extends BaseFragmentPagerAdapter<GoogleImageObject, PortraitFragment> {

    public PortraitsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public PortraitFragment newInstance(GoogleImageObject item) {
        return PortraitFragment.newInstance(item);
    }
}
