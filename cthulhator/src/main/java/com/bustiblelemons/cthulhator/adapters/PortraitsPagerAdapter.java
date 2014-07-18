package com.bustiblelemons.cthulhator.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.bustiblelemons.adapters.viewpager.BaseViewPagerAdapter;
import com.bustiblelemons.cthulhator.fragments.PortraitFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 18.07.14.
 */
public class PortraitsPagerAdapter extends BaseViewPagerAdapter<PortraitFragment> {
    public List<String> urls = new ArrayList<String>();

    public PortraitsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public boolean addData(List<String> urls) {
        boolean ret = this.urls.addAll(urls);
        notifyDataSetChanged();
        return ret;
    }

    public boolean addData(String url) {
        return urls.add(url);
    }

    @Override
    public PortraitFragment getEmptyFragment() {
        return (PortraitFragment) PortraitFragment.newInstance();
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Fragment getItem(int position) {
        if (!hasFragment(position)) {
            addFragment(position, PortraitFragment.newInstance(urls.get(position)));
        }
        return super.getItem(position);
    }
}
