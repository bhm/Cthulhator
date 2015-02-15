package com.bustiblelemons.cthulhator.character.viewer.logic;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.cthulhator.character.viewer.CharacterViewerCard;
import com.bustiblelemons.cthulhator.system.properties.PropertyValueRetreiver;
import com.bustiblelemons.recycler.AbsRecyclerHolder;
import com.bustiblelemons.views.SkillView;
import com.bustiblelemons.views.StatisticView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hiv on 10.12.14.
 */
public class CharacterViewerCardHolder extends AbsRecyclerHolder<CharacterViewerCard> {

    private PropertyValueRetreiver mRetreiver;
    private HashMap<String, View> viewMap = new HashMap<String, View>();


    public CharacterViewerCardHolder(View view) {
        super(view);
        if (view instanceof ViewGroup) {
            findTaggedChildren((ViewGroup) view);
        }
    }

    private void findTaggedChildren(ViewGroup root) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View child = root.getChildAt(i);
            if (child instanceof SkillView
                    || child instanceof StatisticView) {
                Object tag = child.getTag();
                if (tag instanceof String) {
                    String s = (String) tag;
                    if (!TextUtils.isEmpty(s)) {
                        viewMap.put(s, child);
                    }
                }
            } else if (child instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) child;
                findTaggedChildren(group);
            }
        }
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
        for (Map.Entry<String, View> e : viewMap.entrySet()) {
            View v = e.getValue();
            String name = e.getKey();
            int val = mRetreiver.onRetreivePropertValue(name);
            if (v instanceof StatisticView) {
                StatisticView statisticView = (StatisticView) v;
                statisticView.setIntValue(val);
            } else if (v instanceof SkillView) {
                SkillView skillView = (SkillView) v;
                skillView.setIntValue(val);
            }
        }
    }
}
