package com.bustiblelemons.cthulhator.character.creation.logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.creation.model.CreatorCard;
import com.bustiblelemons.recycler.AbsRecyclerAdapter;

import java.util.List;

/**
 * Created by hiv on 02.11.14.
 */
public class CreatorAdapter extends AbsRecyclerAdapter<CreatorCard, CreatorCardHolder> {

    private Context mContext;

    public CreatorAdapter(List<CreatorCard> data, Context context) {
        super(data);
        mContext = context;
    }

    @Override
    public CreatorCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_chracteristic_card, parent, false);
        return new CreatorCardHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(CreatorCardHolder creatorCardHolder, int position) {
        creatorCardHolder.bindData(getItem(position));
    }
}
