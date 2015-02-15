package com.bustiblelemons.cthulhator.character.viewer.logic;

import android.text.TextUtils;
import android.view.View;

import com.bustiblelemons.cthulhator.character.viewer.CharacterViewerCard;
import com.bustiblelemons.cthulhator.system.properties.PropertyValueRetreiver;

import java.util.Collection;

/**
 * Created by hiv on 10.12.14.
 */
public class MindViewerCardHolder extends CharacterViewerCardHolder {

    private PropertyValueRetreiver mRetreiver;



    public MindViewerCardHolder(View view) {
        super(view);
    }

    public MindViewerCardHolder withPropertyValueRetreiver(PropertyValueRetreiver retreiver) {
        mRetreiver = retreiver;
        return this;
    }


    @Override
    public void onBindData(CharacterViewerCard item) {
        if (mRetreiver == null) {
            return;
        }
        if (item != null && item.getPropertyNames() != null) {
            Collection<String> propertyNames = item.getPropertyNames();
            for (String propName : propertyNames) {
                setupPropertyView(propName);
            }
        }
    }

    private void setupPropertyView(String propName) {
        if (!TextUtils.isEmpty(propName)) {            ;
//            if (propertyView instanceof StatisticView) {
//                StatisticView view = (StatisticView) propertyView;
//                int value = mRetreiver.onRetreivePropertValue(propName);
//                view.setValue(value + "");
//                sStatViewsCache.put(propName, view);
//            } else if (propertyView instanceof SkillView) {
//                SkillView view = (SkillView) propertyView;
//                int value = mRetreiver.onRetreivePropertValue(propName);
//                view.setIntValue(value);
//                sSkillViewsCache.put(propName, view);
//            }
        }
    }
}
