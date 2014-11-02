package com.bustiblelemons.cthulhator.character.creation.logic;

import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.creation.model.CreatorCard;
import com.bustiblelemons.cthulhator.view.characteristiccard.CharacteristicCard;
import com.bustiblelemons.recycler.AbsRecyclerHolder;

/**
 * Created by hiv on 02.11.14.
 */
public class CreatorCardHolder extends AbsRecyclerHolder<CreatorCard> {

    private CharacteristicCard characteristicCard;

    public CreatorCardHolder(View parent) {
        super(parent);
        characteristicCard = (CharacteristicCard)
                getInflater().inflate(R.layout.single_chracteristic_card, null);

    }

    @Override
    public void bindData(CreatorCard item) {
        if (item != null) {
            characteristicCard.setProperties(item.getProperties());
        }
    }
}
