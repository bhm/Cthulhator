package com.bustiblelemons.cthulhator.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.history.model.YearsPeriod;
import com.bustiblelemons.holders.impl.AbsViewHolder;

import java.util.Locale;

import butterknife.InjectView;

/**
 * Created by bhm on 07.08.14.
 */
public class YearsPeriodHolder extends AbsViewHolder<YearsPeriod> {

    private final boolean showYears;
    @InjectView(android.R.id.custom)
    TextView yearsView;

    public YearsPeriodHolder(Context context, boolean showYears) {
        super(context);
        this.showYears = showYears;
    }

    @Override
    public void bindValues(YearsPeriod item, int position) {
        setTitle(item.getName());
        if (yearsView != null) {
            yearsView.setVisibility(showYears ? View.VISIBLE : View.GONE);
            if (showYears) {
                int min = item.getMinYear();
                int max = item.getMaxYear();
                String format = max == min ? "%s" : "%s-%s";
                String years = String.format(Locale.ENGLISH, format, min, max);
                yearsView.setText(years);
            } else {
                yearsView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.single_year_period_item;
    }
}
