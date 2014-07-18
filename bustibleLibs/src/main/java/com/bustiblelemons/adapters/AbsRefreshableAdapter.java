package com.bustiblelemons.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class AbsRefreshableAdapter<T> extends BaseAdapter {

    private static final String TAG = AbsRefreshableAdapter.class.getSimpleName();
    protected Context        context;
    protected LayoutInflater inflater;
    protected List<T>        data;


    public Context getContext() {
        return context;
    }

    protected AbsRefreshableAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public AbsRefreshableAdapter(Context context, List<T> data) {
        this(context);
        this.data = data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean refreshData() {
        notifyDataSetChanged(); return true;
    }

    public void refreshData(List<T> data) {
        setData(data);
        notifyDataSetChanged();
    }


    protected abstract int getItemLayoutId();


}