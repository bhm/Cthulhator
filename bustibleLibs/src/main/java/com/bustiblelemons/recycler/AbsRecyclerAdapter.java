package com.bustiblelemons.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hiv on 02.11.14.
 */
public abstract class AbsRecyclerAdapter<I, VH extends AbsRecyclerHolder<I>>
        extends RecyclerView.Adapter<VH> {

    private List<I> mData;

    private Context context;

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public boolean addItem(I item) {
        if (item == null) {
            return false;
        }
        boolean ret = this.mData.add(item);
        notifyDataSetChanged();
        return ret;
    }

    public boolean addItems(Collection<I> items) {
        boolean ret = this.mData.addAll(items);
        notifyDataSetChanged();
        return ret;
    }

    public boolean addItems(I... items) {
        boolean ret = Collections.addAll(mData, items);
        notifyDataSetChanged();
        return ret;
    }

    public List<I> getItems() {
        return this.mData;
    }

    public boolean removeItem(I item) {
        boolean ret = this.mData.remove(item);
        notifyDataSetChanged();
        return ret;
    }

    public boolean removeItems(Collection<I> items) {
        boolean r = this.mData.removeAll(items);
        notifyDataSetChanged();
        return r;
    }

    public List<I> getSelectedItems(SparseBooleanArray checkedItems) {
        List<I> result = new ArrayList<I>();
        if (checkedItems != null) {
            for (int i = 0; i < mData.size(); i++) {
                if (checkedItems.get(i)) {
                    result.add(getItem(i));
                }
            }
        }
        return result;
    }

    public I getItem(int i) {
        if (mData != null) {
            return mData.get(i);
        }
        return null;
    }

    public boolean refreshData() {
        notifyDataSetChanged();
        return false;
    }

    public boolean refreshData(Collection<I> data) {
        setData(data);
        notifyDataSetChanged();
        return false;
    }

    public List<I> getData() {
        return this.mData;
    }

    protected void setData(Collection<I> data) {
        this.mData = new LinkedList<I>(data);
    }

    public void removeItem(int pos) {
        removeItem(getItem(pos));
    }

    public void add(List<Integer> positions, List<I> items) {
        int threshold = positions.size() > items.size() ? items.size() : positions.size();
        for (int i = 0; i < threshold; i++) {
            getData().add(positions.get(i), items.get(i));
        }
        notifyDataSetChanged();
    }

    public void removeItems(int... positions) {
        for (int position : positions) {
            getData().remove(position);
        }
        notifyDataSetChanged();
    }

    public void removeAll() {
        mData.clear();
        notifyDataSetChanged();
    }
}
