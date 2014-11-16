package com.bustiblelemons.cthulhator.character.creation.logic;

import android.content.Context;
import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.creation.model.CreatorCard;
import com.bustiblelemons.cthulhator.view.characteristiccard.CharacteristicCard;
import com.bustiblelemons.recycler.AbsRecyclerAdapter;

/**
 * Created by hiv on 02.11.14.
 */
public class CreatorCardsAdapter extends AbsRecyclerAdapter<CreatorCard, CreatorCardHolder> {

    private RelatedPropertesRetreiver            mRetreiver;
    private CharacteristicCard.OnPropertyChanged mPropertyChanged;

    public CreatorCardsAdapter(Context context) {
        super(context);
    }

    public CreatorCardsAdapter withRelatedPropertiesCallback(RelatedPropertesRetreiver callback) {
        mRetreiver = callback;
        return this;
    }

    public CreatorCardsAdapter withPropertyChangedCallback(CharacteristicCard.OnPropertyChanged callback) {
        mPropertyChanged = callback;
        return this;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.single_chracteristic_card;
    }

    @Override
    public CreatorCardHolder getViewHolder(View view) {
        CreatorCardHolder holder = new CreatorCardHolder(view);
        holder.setRetreiver(mRetreiver);
        holder.setOnPropertyChanged(mPropertyChanged);
        return holder;
    }

}
