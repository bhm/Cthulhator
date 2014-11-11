package com.bustiblelemons.cthulhator.character.creation.logic;

import android.content.Context;
import android.view.View;

import com.bustiblelemons.cthulhator.character.creation.model.CreatorCard;
import com.bustiblelemons.cthulhator.view.characteristiccard.CharacteristicCard;
import com.bustiblelemons.recycler.AbsRecyclerHolder;

/**
 * Created by hiv on 02.11.14.
 */
public class CreatorCardHolder extends AbsRecyclerHolder<CreatorCard> {

    private RelatedPropertesRetreiver            mRetreiver;
    private CharacteristicCard.OnPropertyChanged mOnPropertyChanged;
    private CharacteristicCard mCharacteristicCard;

    public CreatorCardHolder(View itemView, Context context) {
        super(itemView);
    }

    public void setOnPropertyChanged(CharacteristicCard.OnPropertyChanged propertyChanged) {
        this.mOnPropertyChanged = propertyChanged;
    }

    public void setRetreiver(RelatedPropertesRetreiver retreiver) {
        this.mRetreiver = retreiver;
    }

    @Override
    public void bindData(CreatorCard item) {
        if (item != null && mCharacteristicCard != null) {
            mCharacteristicCard.setOnPropertyChanged(mOnPropertyChanged);
            mCharacteristicCard.setRelatedRetreiver(mRetreiver);
            mCharacteristicCard.setProperties(item.getProperties());
        }
    }
}
