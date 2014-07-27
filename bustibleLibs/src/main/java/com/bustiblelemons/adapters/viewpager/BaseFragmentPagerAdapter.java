package com.bustiblelemons.adapters.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 18.07.14.
 */
public abstract class BaseFragmentPagerAdapter<T, F extends Fragment>
        extends FragmentStatePagerAdapter {

    private SparseArray<F> mFragments = new SparseArray<F>();
    private List<T>        mData      = new ArrayList<T>();

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public BaseFragmentPagerAdapter(FragmentManager fm, List<T> data) {
        super(fm);
        this.mData = data;
    }

    public void setFragments(SparseArray<F> fragments) {
        this.mFragments = fragments;
        notifyDataSetChanged();
    }

    public boolean hasFragment(int atPos) {
        return mFragments != null ? mFragments.get(atPos) != null : false;
    }

    public void addData(List<T> data) {
        for (T item : data) {
            mData.add(item);
            int pos = mData.size();
            F f = newInstance(item);
            mFragments.put(pos, f);
        }
        notifyDataSetChanged();
    }

    public boolean addData(T item) {
        boolean r = mData.add(item);
        int pos = mData.size();
        F f = newInstance(item);
        mFragments.put(pos, f);
        notifyDataSetChanged();
        return r;
    }

    public abstract F newInstance(T item);

    @Override
    public F getItem(int position) {
        if (mFragments.get(position) == null) {
            T item = mData.get(position);
            F f = newInstance(item);
            mFragments.put(position, f);
        }
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return this.mData.size();
    }
}
