package com.bustiblelemons.cthulhator.character.creation.logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.creation.model.CreatorCard;
import com.bustiblelemons.cthulhator.view.characteristiccard.CharacteristicCard;
import com.bustiblelemons.recycler.AbsRecyclerAdapter;

/**
 * Created by hiv on 02.11.14.
 */
public class CreatorCardsAdapter extends AbsRecyclerAdapter<CreatorCard, CreatorCardHolder> {


    public CreatorCardsAdapter(Context context) {
        super(context);
    }

    @Override
    public CreatorCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_chracteristic_card, parent, false);
        CreatorCardHolder holder = new CreatorCardHolder(view);
        if (getContext() instanceof RelatedPropertesRetreiver) {
            holder.setOnPropertyChanged((CharacteristicCard.OnPropertyChanged) getContext());
        }
        if (getContext() instanceof CharacteristicCard.OnPropertyChanged) {
            holder.setRetreiver((RelatedPropertesRetreiver) getContext());
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(CreatorCardHolder creatorCardHolder, int position) {
        creatorCardHolder.bindData(getItem(position));
    }
}
