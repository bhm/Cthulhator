package com.bustiblelemons.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bustiblelemons.bustiblelibs.R;
import com.bustiblelemons.holders.impl.ViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by bhm on 20.07.14.
 */
public abstract class AbsListAdapter<T, H extends ViewHolder<T>> extends BaseAdapter {
    public static String TAG;

    {
        TAG = getClass().getSimpleName();
    }

    private List<T> mData;
    private Context        context;
    private LayoutInflater inflater;

    public AbsListAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public AbsListAdapter(Context context, Collection<T> data) {
        this(context);
        this.mData = new ArrayList<T>(data);
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public Context getContext() {
        return context;
    }

    protected abstract H getViewHolder(int position);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        H holder;
        if (convertView == null) {
            holder = getViewHolder(position);
            convertView = inflater.inflate(holder.getLayoutId(position), parent, false);
            holder.create(convertView);
            convertView.setTag(R.id.tag_holder, holder);
        } else {
            holder = (H) convertView.getTag(R.id.tag_holder);
        }
        bindViewHolder(position, holder);
        return convertView;
    }

    protected void bindViewHolder(int position, H holder) {
        holder.bindValues(getItem(position), position);
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public T getItem(int position) {
        if (mData == null) {
            return null;
        }
        return mData.get(position);
    }

    public boolean addItem(T item) {
        if (mData == null) {
            mData = new ArrayList<T>();
        }
        if (item == null) {
            return false;
        }
        boolean ret = this.mData.add(item);
        notifyDataSetChanged();
        return ret;
    }

    public boolean addItems(Collection<T> items) {
        if (items == null) {
            throw new NullPointerException("params cannot be null");
        }
        if (mData == null) {
            mData = new ArrayList<T>(items.size());
        }
        boolean ret = this.mData.addAll(items);
        notifyDataSetChanged();
        return ret;
    }

    public boolean addItems(@NonNull T... items) {
        if (items == null) {
            throw new NullPointerException("params cannot be null");
        }
        if (mData == null) {
            mData = new ArrayList<T>(items.length);
        }
        boolean ret = Collections.addAll(mData, items);
        notifyDataSetChanged();
        return ret;
    }

    public boolean removeItem(T item) {
        if (mData == null) {
            return false;
        }
        boolean ret = this.mData.remove(item);
        notifyDataSetChanged();
        return ret;
    }

    public boolean removeItems(Collection<T> items) {
        if (mData == null) {
            return false;
        }
        boolean r = this.mData.removeAll(items);
        notifyDataSetChanged();
        return r;
    }

    public List<T> getSelectedItems(SparseBooleanArray checkedItems) {
        if (checkedItems == null) {
            throw new NullPointerException("params cannot be null");
        }
        if (mData == null) {
            return Collections.emptyList();
        }
        List<T> result = new ArrayList<T>();
        if (checkedItems != null) {
            for (int i = 0; i < mData.size(); i++) {
                if (checkedItems.get(i)) {
                    result.add(getItem(i));
                }
            }
        }
        return result;
    }

    public boolean refreshData() {
        notifyDataSetChanged();
        return false;
    }

    public boolean refreshData(Collection<T> data) {
        setData(data);
        notifyDataSetChanged();
        return false;
    }

    public List<T> getData() {
        return this.mData;
    }

    protected void setData(Collection<T> data) {
        this.mData = new ArrayList<T>(data);
    }

    public void removeItem(int pos) {
        removeItem(getItem(pos));
    }

    public void add(List<Integer> positions, List<T> items) {
        int threshold = positions.size() > items.size() ? items.size() : positions.size();
        for (int i = 0; i < threshold; i++) {
            getData().add(positions.get(i), items.get(i));
        }
        notifyDataSetChanged();
    }

    public void removeItems(int... positions) {
        if (mData == null) {
            return;
        }
        for (int position : positions) {
            if (mData.size() < position) {
                mData.remove(position);
            }
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