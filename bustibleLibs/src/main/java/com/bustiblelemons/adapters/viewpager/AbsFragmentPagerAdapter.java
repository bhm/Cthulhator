package com.bustiblelemons.adapters.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bhm on 18.07.14.
 */
public abstract class AbsFragmentPagerAdapter<T, F extends Fragment>
        extends FragmentStatePagerAdapter {

    private SparseArray<F> mFragments = new SparseArray<F>();
    private List<T>        mData      = new ArrayList<T>();

    public AbsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public AbsFragmentPagerAdapter(FragmentManager fm, List<T> data) {
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

    public void addData(T... data) {
        if (mData == null) {
            mData = new ArrayList<T>();
        }
        Collections.addAll(mData, data);
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        if (mData == null) {
            mData = new ArrayList<T>();
        }
        mData.addAll(data);

        notifyDataSetChanged();
    }

    public boolean addData(T item) {
        boolean r = mData.add(item);
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

    public T getItemObject(int position) {
        return mData != null && mData.size() > 0 ? mData.get(position) : null;
    }


    public void removeAll() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(position);
    }
}
