package com.bustiblelemons.cthulhator.adapters;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.bustiblelemons.adapters.AbsSpinnerAdapter;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.model.time.CthulhuPeriod;
import com.bustiblelemons.cthulhator.model.time.YearsPeriod;
import com.bustiblelemons.holders.impl.ViewHolder;

/**
 * Created by bhm on 03.08.14.
 */
public class PeriodSpinnerAdapter extends AbsSpinnerAdapter<YearsPeriod>
        implements AdapterView.OnItemSelectedListener {

    private OnYearsPeriodSelected onYearsPeriodSelected;

    public PeriodSpinnerAdapter(Context context, OnYearsPeriodSelected listener) {
        super(context);
        this.onYearsPeriodSelected = listener;
        addItems(CthulhuPeriod.values());
    }

    public void setOnYearsPeriodSelected(OnYearsPeriodSelected onYearsPeriodSelected) {
        this.onYearsPeriodSelected = onYearsPeriodSelected;
    }

    @Override
    public ViewHolder<YearsPeriod> getDropDownHolder(int position) {
        return new YearsPeriodHolder(getContext(), true);
    }

    @Override
    public int getDropDownLayoutId(int position) {
        return R.layout.single_year_period_item;
    }

    @Override
    protected ViewHolder<YearsPeriod> getViewHolder(int position) {
        return new YearsPeriodHolder(getContext(), false);
    }

    @Override
    protected int getItemLayoutId(int position) {
        return R.layout.single_year_period_item;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (onYearsPeriodSelected != null) {
            onYearsPeriodSelected.onYearsPeriodSelected(getItem(position));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (onYearsPeriodSelected != null) {
            onYearsPeriodSelected.onYearsPeriodSelected(getItem(0));
        }
    }

    public interface OnYearsPeriodSelected {
        void onYearsPeriodSelected(YearsPeriod period);
    }
}