package com.bustiblelemons.cthulhator.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.bustiblelemons.adapters.viewpager.BaseViewPagerAdapter;
import com.bustiblelemons.cthulhator.fragments.PortraitFragment;
import com.bustiblelemons.google.apis.model.GoogleImageObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 18.07.14.
 */
public class PortraitsPagerAdapter extends BaseViewPagerAdapter<PortraitFragment> {
    private List<GoogleImageObject> data = new ArrayList<GoogleImageObject>();

    public PortraitsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setUrls(List<GoogleImageObject> data) {
        this.data = data;
    }

    public boolean addData(List<GoogleImageObject> data) {
        boolean ret = this.data.addAll(data);
        notifyDataSetChanged();
        return ret;
    }

    public boolean addData(GoogleImageObject item) {
        return data.add(item);
    }

    @Override
    public PortraitFragment getEmptyFragment() {
        return (PortraitFragment) PortraitFragment.newInstance();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Fragment getItem(int position) {
        if (!hasFragment(position)) {
            GoogleImageObject object = data.get(position);
            PortraitFragment frag = PortraitFragment.newInstance(object);
            addFragment(position, frag);
        }
        return super.getItem(position);
    }
}
