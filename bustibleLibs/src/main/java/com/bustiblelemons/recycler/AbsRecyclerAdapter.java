package com.bustiblelemons.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by hiv on 02.11.14.
 */
public abstract class AbsRecyclerAdapter<I, VH extends AbsRecyclerHolder<I>>
        extends RecyclerView.Adapter<VH> {

    private List<I> mData = new ArrayList<I>(0);

    public AbsRecyclerAdapter() {

    }

    public abstract int getLayoutId(int viewType);

    public abstract VH getViewHolder(View view, int viewType);

    @Override
    public VH onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(getLayoutId(viewType), viewGroup, false);
        return getViewHolder(view, viewType);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public void onBindViewHolder(VH vh, int position) {
        if (vh != null) {
            vh.onBindData(getItem(position));
        }
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

    public boolean addItems(@NonNull Collection<I> items) {
        if (items == null) {
            throw new NullPointerException("param cannot be null");
        }
        if (mData == null) {
            mData = new ArrayList<I>(items.size());
        }
        boolean ret = this.mData.addAll(items);
        notifyDataSetChanged();
        return ret;
    }

    public boolean addItems(@NonNull I... items) {
        if (items == null) {
            throw new NullPointerException("param cannot be null");
        }
        if (mData == null) {
            mData = new ArrayList<I>(items.length);
        }
        boolean ret = Collections.addAll(mData, items);
        notifyDataSetChanged();
        return ret;
    }

    public List<I> getItems() {
        return this.mData;
    }

    public boolean removeItem(I item) {
        if (mData == null) {
            return false;
        }
        boolean ret = this.mData.remove(item);
        notifyDataSetChanged();
        return ret;
    }

    public boolean removeItems(Collection<I> items) {
        if (items == null) {
            throw new NullPointerException("param cannot be null");
        }
        if (mData == null) {
            mData = new ArrayList<I>(0);
        }
        boolean r = this.mData.removeAll(items);
        notifyDataSetChanged();
        return r;
    }

    public List<I> getSelectedItems(SparseBooleanArray checkedItems) {
        if (checkedItems == null) {
            throw new NullPointerException("param cannot be null");
        }
        if (mData == null || mData != null && mData.size() == 0) {
            return Collections.emptyList();
        }
        List<I> result = new ArrayList<I>(checkedItems.size());
        for (int i = 0; i < mData.size(); i++) {
            if (checkedItems.get(i)) {
                result.add(getItem(i));
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

    public void refreshData() {
        notifyDataSetChanged();
    }

    public void refreshData(Collection<I> data) {
        setData(data);
        notifyDataSetChanged();
    }

    public void refreshData(I[] items) {
        if (items != null && items.length > 0) {
            refreshData(Arrays.asList(items));
        }
    }

    public List<I> getData() {
        return this.mData;
    }

    protected void setData(@NonNull Collection<I> data) {
        if (data == null) {
            throw new NullPointerException("param cannot be null");
        }
        this.mData = new ArrayList<I>(data);
    }

    public void removeItem(int pos) {
        removeItem(getItem(pos));
    }

    public void add(List<Integer> positions, List<I> items) {
        if (positions == null && items != null) {
            if (positions.size() == 0 || items.size() == 0) {
                return;
            }
        }
        int threshold = positions.size() > items.size() ? items.size() : positions.size();
        if (mData == null) {
            int maxPos = 0;
            for (Integer pos : positions) {
                if (pos != null && pos.intValue() > maxPos) {
                    maxPos = pos.intValue();
                }
            }
            mData = new ArrayList<I>(maxPos);
        }
        for (int i = 0; i < threshold; i++) {
            Integer pos = positions.get(i);
            if (pos == null) {
                continue;
            }
            mData.add(pos.intValue(), items.get(i));
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
        if (mData == null) {
            return;
        }
        mData.clear();
        notifyDataSetChanged();
    }
}
