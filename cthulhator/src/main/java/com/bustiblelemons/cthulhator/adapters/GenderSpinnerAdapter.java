package com.bustiblelemons.cthulhator.adapters;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.bustiblelemons.adapters.AbsSpinnerAdapter;
import com.bustiblelemons.cthulhator.character.portrait.logic.GImageGenderHolder;
import com.bustiblelemons.google.apis.GoogleSearchGender;
import com.bustiblelemons.holders.impl.ViewHolder;

import java.util.Arrays;

/**
 * Created by bhm on 27.07.14.
 */
public class GenderSpinnerAdapter extends AbsSpinnerAdapter<GoogleSearchGender>
        implements AdapterView.OnItemSelectedListener {

    private GenderSelected listener;

    public GenderSpinnerAdapter(Context context, GenderSelected listener) {
        super(context, Arrays.asList(GoogleSearchGender.values()));
        this.listener = listener;
    }

    @Override
    public ViewHolder<GoogleSearchGender> getDropDownHolder(int position) {
        return new GImageGenderHolder(getContext());
    }

    @Override
    protected ViewHolder<GoogleSearchGender> getViewHolder(int position) {
        return new GImageGenderHolder(getContext());
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
        boolean onGenderSelected(GoogleSearchGender googleSearchGender);
    }
}
