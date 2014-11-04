package com.bustiblelemons.cthulhator.view.characteristiccard;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.creation.logic.RelatedPropertesRetreiver;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.utils.ResourceHelper;
import com.bustiblelemons.views.SkillView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hiv on 02.11.14.
 */
public class CharacteristicCard extends RelativeLayout implements SkillView.OnValueButtonsClicked {
    private View rootView;
    private int  defValSize;
    private int  defTitleSize;

    private List<CharacterProperty> mPrimaryPropList;
    private ViewGroup               mPrimaryList;
    private ViewGroup               mSecondaryList;
    private List<CharacterProperty> mSecondaryPropList;

    private Map<CharacterProperty, SkillView> mPropertyToView;

    private RelatedPropertesRetreiver mRelatedRetreiver;
    private OnPropertyChanged mOnPropertyChanged;

    public CharacteristicCard(Context context) {
        super(context);
        init(context, null);
    }

    public CharacteristicCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public CharacteristicCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CharacteristicCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rootView = LayoutInflater.from(context).inflate(R.layout.characteristics_card, this, false);
        mPrimaryList = (ViewGroup) rootView.findViewById(android.R.id.primary);
        mSecondaryList = (ViewGroup) rootView.findViewById(android.R.id.list);
        setupDefaultTextSize(context);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CharacteristicCard);
            array.recycle();
        }
        addView(rootView);
    }

    private void setupDefaultTextSize(Context context) {
        defTitleSize = context.getResources().getDimensionPixelSize(R.dimen.font_16);
        defValSize = context.getResources().getDimensionPixelSize(R.dimen.font_16);
    }

    public List<CharacterProperty> getProperties() {
        return mPrimaryPropList;
    }

    public void setProperties(Collection<CharacterProperty> properties) {
        this.mPrimaryPropList = new ArrayList<CharacterProperty>(properties);
        mSecondaryPropList = new ArrayList<CharacterProperty>();
        rRefreshCharacterProperties();
        refreshSecondaryList();
    }

    private int rRefreshCharacterProperties() {
        int r = 0;
        mPrimaryList.removeAllViews();
        for (CharacterProperty property : mPrimaryPropList) {
            if (property != null) {
                addCharacterPropertyView(property);
                r++;
            }
        }
        return r;
    }

    private void addCharacterPropertyView(CharacterProperty property) {
        if (property == null) {
            throw new IllegalArgumentException("property == null");
        }
        SkillView skillView = (SkillView)
                LayoutInflater.from(getContext())
                        .inflate(R.layout.single_chracteristic_card_primary, mPrimaryList, false);
        if (skillView != null) {
            skillView.setMinValue(property.getMinValue());
            skillView.setMaxValue(property.getMaxValue());
            skillView.setOnValueButtonsClicked(this);
            skillView.setTag(R.id.tag_property, property);
            int nameResId = ResourceHelper.from(getContext())
                    .getIdentifierForStringByNameParts("stat", property.getName());
            if (nameResId > 0) {
                property.setNameResId(nameResId);
                skillView.setTitle(nameResId);
            } else {
                skillView.setTitle(property.getName());
            }
            int propValue = property.getValue() == 0 ? property.randomValue() : property.getValue();
            skillView.setIntValue(propValue);
            mPrimaryList.addView(skillView);
            addRelatedProperties(property);
        }
    }

    private void addRelatedProperties(CharacterProperty property) {
        if (mRelatedRetreiver != null) {
            if (mSecondaryPropList == null) {
                mSecondaryPropList = new ArrayList<CharacterProperty>();
            }
            mSecondaryPropList.addAll(mRelatedRetreiver.getRelatedPropertes(property));
        }
    }

    private void refreshSecondaryList() {
        if (mPropertyToView == null) {
            mPropertyToView = new HashMap<CharacterProperty, SkillView>();
        }
        for (CharacterProperty property : mSecondaryPropList) {
            if (property != null) {
                SkillView view = mPropertyToView.get(property);
                if (view == null) {
                    view = (SkillView) LayoutInflater.from(getContext())
                            .inflate(R.layout.single_character_property, mSecondaryList, false);
                    mSecondaryList.addView(view);
                    mPropertyToView.put(property, view);
                }
                view.setTitle(property.getName());
                view.setIsPercentile(property.isPercentile());
                view.setIntValue(property.getValue());
                view.setValue(property.getDisplayValue());
            }
        }
    }

    public void setRelatedRetreiver(RelatedPropertesRetreiver relatedRetreiver) {
        mRelatedRetreiver = relatedRetreiver;
    }

    @Override
    public boolean onIncreaseClicked(SkillView view) {
        if (view.canIncrease()) {
            increasePropertyAndUpdateView(view);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDecreaseClicked(SkillView view) {
        if (view.canDecrease()) {
            decreasePropertyAndUpdateView(view);
            return true;
        }
        return false;
    }

    private void increasePropertyAndUpdateView(SkillView view) {
        CharacterProperty property = (CharacterProperty) view.getTag(R.id.tag_property);
        if (property != null && property.increaseValue()) {
            view.setIntValue(property.getValue());
            broadcastPropertyChange(property);
            updateRelated();
            refreshSecondaryList();
        }
    }

    private void decreasePropertyAndUpdateView(SkillView view) {
        CharacterProperty property = (CharacterProperty) view.getTag(R.id.tag_property);
        if (property != null && property.decreaseValue()) {
            view.setIntValue(property.getValue());
            broadcastPropertyChange(property);
            updateRelated();
            refreshSecondaryList();
        }
    }

    private void updateRelated() {
        if (mRelatedRetreiver != null) {
            mSecondaryPropList.clear();
            for (CharacterProperty primaryPropery : mPrimaryPropList) {
                if (primaryPropery != null) {
                    Collection<CharacterProperty> r =
                            mRelatedRetreiver.getRelatedPropertes(primaryPropery);
                    mSecondaryPropList.addAll(r);
                }
            }
        }
    }

    private void broadcastPropertyChange(CharacterProperty property) {
        if (mOnPropertyChanged != null) {
            mOnPropertyChanged.onPropertyChanged(property);
        }
    }

    public void setOnPropertyChanged(OnPropertyChanged onPropertyChanged) {
        this.mOnPropertyChanged = onPropertyChanged;
    }

    public interface OnPropertyChanged {
        void onPropertyChanged(CharacterProperty property);
    }
}
