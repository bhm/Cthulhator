package com.bustiblelemons.adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bustiblelemons.bustiblelibs.R;
import com.bustiblelemons.holders.impl.ViewHolder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by bhm on 20.07.14.
 */
public abstract class AbsListAdapter<T, H extends ViewHolder<T>> extends BaseAdapter {
    public static String TAG;

    {
        TAG = getClass().getSimpleName();
    }

    private List<T> data = new ArrayList<T>();
    private Context        context;
    private LayoutInflater inflater;

    public AbsListAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public AbsListAdapter(Context context, List<T> data) {
        this(context);
        this.data = new LinkedList<T>(data);
    }


    public LayoutInflater getInflater() {
        return inflater;
    }

    public Context getContext() {
        return context;
    }

    protected abstract H getViewHolder(int position);

    protected abstract int getItemLayoutId(int position);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        H holder;
        if (convertView == null) {
            holder = getViewHolder(position);
            convertView = inflater.inflate(getItemLayoutId(position), parent, false);
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
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }


    public boolean addItems(List<T> data) {
        boolean ret = data.addAll(data);
        notifyDataSetChanged();
        return ret;
    }

    public boolean addItem(T item) {
        boolean ret = data.add(item);
        notifyDataSetChanged();
        return ret;
    }

    public List<T> getItems() {
        return this.data;
    }

    public boolean removeItem(T item) {
        boolean ret = data.remove(item);
        notifyDataSetChanged();
        return ret;
    }

    public boolean removeItems(List<T> items) {
        boolean r = data.removeAll(items);
        notifyDataSetChanged();
        return r;
    }

    public List<T> getSelectedItems(SparseBooleanArray checkedItems) {
        List<T> result = new ArrayList<T>();
        if (checkedItems != null) {
            for (int i = 0; i < data.size(); i++) {
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

    public boolean refreshData(List<T> data) {
        setData(data);
        notifyDataSetChanged();
        return false;
    }

    public List<T> getData() {
        return this.data;
    }

    protected void setData(List<T> data) {
        this.data = new LinkedList<T>(data);
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
        for (int position : positions) {
            getData().remove(position);
        }
        notifyDataSetChanged();
    }
}