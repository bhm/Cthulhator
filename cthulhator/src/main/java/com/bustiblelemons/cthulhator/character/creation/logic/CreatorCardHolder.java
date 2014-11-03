package com.bustiblelemons.cthulhator.character.creation.logic;

import android.view.View;

import com.bustiblelemons.cthulhator.character.creation.model.CreatorCard;
import com.bustiblelemons.cthulhator.view.characteristiccard.CharacteristicCard;
import com.bustiblelemons.recycler.AbsRecyclerHolder;

/**
 * Created by hiv on 02.11.14.
 */
public class CreatorCardHolder extends AbsRecyclerHolder<CreatorCard> {

    private RelatedPropertesRetreiver mRetreiver;
    private CharacteristicCard        characteristicCard;

    public CreatorCardHolder(View itemView, RelatedPropertesRetreiver retreiver) {
        super(itemView);
        characteristicCard = (CharacteristicCard) itemView;
        this.mRetreiver = retreiver;
    }

    @Override
    public void bindData(CreatorCard item) {
        if (item != null && characteristicCard != null) {
            characteristicCard.setProperties(item.getProperties());
            characteristicCard.setRelatedRetreiver(mRetreiver);
        }
    }
}
