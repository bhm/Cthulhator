package com.bustiblelemons.cthulhator.character.viewer.logic;

import android.text.TextUtils;
import android.view.View;

import com.bustiblelemons.cthulhator.character.viewer.CharacterViewerCard;
import com.bustiblelemons.cthulhator.system.properties.PropertyValueRetreiver;
import com.bustiblelemons.recycler.AbsRecyclerHolder;
import com.bustiblelemons.views.StatisticView;

import java.util.Collection;

/**
 * Created by hiv on 10.12.14.
 */
public class CharacterViewerCardHolder extends AbsRecyclerHolder<CharacterViewerCard> {

    private View                   mView;
    private PropertyValueRetreiver mRetreiver;

    public CharacterViewerCardHolder(View view) {
        super(view);
        mView = view;
    }

    public CharacterViewerCardHolder withPropertyValueRetreiver(PropertyValueRetreiver retreiver) {
        mRetreiver = retreiver;
        return this;
    }


    @Override
    public void onBindData(CharacterViewerCard item) {
        if (mRetreiver == null) {
            return;
        }
        if (item != null && mView != null && item.getPropertyNames() != null) {
            Collection<String> propertyNames = item.getPropertyNames();
            for (String propName : propertyNames) {
                setupPropertyView(propName);
            }
        }
    }

    private void setupPropertyView(String propName) {
        if (!TextUtils.isEmpty(propName)) {
            View propertyView = mView.findViewWithTag(propName);
            if (propertyView instanceof StatisticView) {
                StatisticView view = (StatisticView) propertyView;
                int value = mRetreiver.onRetreivePropertValue(propName);
                view.setValue(value + "");
            }
        }
    }
}
