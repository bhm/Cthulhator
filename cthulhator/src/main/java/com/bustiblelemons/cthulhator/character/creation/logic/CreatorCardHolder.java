package com.bustiblelemons.cthulhator.character.creation.logic;

import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.creation.model.CreatorCard;
import com.bustiblelemons.cthulhator.view.characteristiccard.CharacteristicCard;
import com.bustiblelemons.recycler.AbsRecyclerHolder;
import com.bustiblelemons.recycler.LayoutResId;

/**
 * Created by hiv on 02.11.14.
 */
@LayoutResId(R.layout.single_chracteristic_card)
public class CreatorCardHolder extends AbsRecyclerHolder<CreatorCard> {

    private RelatedPropertesRetreiver            mRetreiver;
    private CharacteristicCard.OnPropertyChanged mOnPropertyChanged;
    private CharacteristicCard                   mCharacteristicCard;

    public CreatorCardHolder(View itemView) {
        super(itemView);
        mCharacteristicCard = (CharacteristicCard) itemView;
    }

    public void setOnPropertyChanged(CharacteristicCard.OnPropertyChanged propertyChanged) {
        this.mOnPropertyChanged = propertyChanged;
    }

    public void setRetreiver(RelatedPropertesRetreiver retreiver) {
        this.mRetreiver = retreiver;
    }

    @Override
    public void onBindData(CreatorCard item) {
        if (item != null && mCharacteristicCard != null) {
            mCharacteristicCard.setOnPropertyChanged(mOnPropertyChanged);
            mCharacteristicCard.setRelatedRetreiver(mRetreiver);
            mCharacteristicCard.setProperties(item.getProperties());
        }
    }
}
