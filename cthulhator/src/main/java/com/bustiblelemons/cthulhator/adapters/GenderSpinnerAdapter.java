package com.bustiblelemons.cthulhator.adapters;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.bustiblelemons.adapters.AbsSpinnerAdapter;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.holders.GImageGenderHolder;
import com.bustiblelemons.cthulhator.model.brp.gimagesearch.GImageSearchGender;
import com.bustiblelemons.holders.impl.ViewHolder;

import java.util.Arrays;

/**
 * Created by bhm on 27.07.14.
 */
public class GenderSpinnerAdapter extends AbsSpinnerAdapter<GImageSearchGender>
        implements AdapterView.OnItemSelectedListener {

    private GenderSelected listener;

    public GenderSpinnerAdapter(Context context, GenderSelected listener) {
        super(context, Arrays.asList(GImageSearchGender.values()));
        this.listener = listener;
    }

    @Override
    public ViewHolder<GImageSearchGender> getDropDownHolder(int position) {
        return new GImageGenderHolder(getContext());
    }

    @Override
    public int getDropDownLayoutId(int position) {
        return R.layout.single_drop_down_gender;
    }

    @Override
    protected ViewHolder<GImageSearchGender> getViewHolder(int position) {
        return new GImageGenderHolder(getContext());
    }

    @Override
    protected int getItemLayoutId(int position) {
        return R.layout.single_drop_down_gender;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (listener != null) {
            listener.onGenderSelected(getItem(position));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface GenderSelected {
        boolean onGenderSelected(GImageSearchGender gender);
    }
}
