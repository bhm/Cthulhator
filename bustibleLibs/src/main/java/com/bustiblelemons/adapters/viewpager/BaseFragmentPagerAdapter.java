package com.bustiblelemons.adapters.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

/**
 * Created by bhm on 18.07.14.
 */
public abstract class BaseFragmentPagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    private SparseArray<T> fragments = new SparseArray<T>();

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(SparseArray<T> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public boolean hasFragment(int atPos) {
        return fragments != null ? fragments.get(atPos) != null : false;
    }

    public abstract T getEmptyFragment();

    @Override
    public Fragment getItem(int position) {
        return fragments != null ? fragments.get(position) : null;
    }

    protected void addFragment(int pos, T fragment) {
        fragments.put(pos, fragment);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
