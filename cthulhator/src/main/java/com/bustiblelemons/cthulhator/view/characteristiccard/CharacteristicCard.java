package com.bustiblelemons.cthulhator.view.characteristiccard;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.creation.logic.RelatedPropertesRetreiver;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.utils.ResourceHelper;
import com.bustiblelemons.views.SkillView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by hiv on 02.11.14.
 */
public class CharacteristicCard extends RelativeLayout {
    private View           rootView;
    private int            defValSize;
    private int            defTitleSize;

    private List<CharacterProperty> mPrimaryPropList;
    private ViewGroup               mPrimaryList;
    private ViewGroup               mSecondaryList;
    private List<CharacterProperty> mSecondaryPropList;
    private ListAdapter             mListAdapter;
    private DataSetObserver mSecondaryListObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            CharacteristicCard.this.refreshSecondaryListData();
            CharacteristicCard.this.populateSecondaryList();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            CharacteristicCard.this.refreshSecondaryListData();
            CharacteristicCard.this.populateSecondaryList();
        }
    };
    private RelatedPropertesRetreiver mRelatedRetreiver;

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

    public ListAdapter getAdapter() {
        return mListAdapter;
    }

    private void setAdapter(ListAdapter adapter) {
        if (isInEditMode()) {
            return;
        }
        if (mListAdapter != null) {
            mListAdapter.unregisterDataSetObserver(mSecondaryListObserver);
        }
        mListAdapter = adapter;
        mListAdapter.registerDataSetObserver(mSecondaryListObserver);
    }

    private void refreshSecondaryListData() {
        if (mSecondaryPropList == null) {
            mSecondaryPropList = new ArrayList<CharacterProperty>();
        }
        for (CharacterProperty property : mPrimaryPropList) {
            if (property != null && mRelatedRetreiver != null) {
                mSecondaryPropList.addAll(mRelatedRetreiver.getRelatedPropertes(property));
            }
        }
    }

    public List<CharacterProperty> getProperties() {
        return mPrimaryPropList;
    }

    public void setProperties(Collection<CharacterProperty> properties) {
        mPrimaryList.removeAllViews();
        this.mPrimaryPropList = new ArrayList<CharacterProperty>(properties);
        rRefreshCharacterProperties();
        populateSecondaryList();
    }

    private int rRefreshCharacterProperties() {
        int r = 0;
        for (CharacterProperty property : mPrimaryPropList) {
            if (property != null) {
                addCharacterPropertyView(property);
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
                        .inflate(R.layout.single_chracteristic_card_secondary, mPrimaryList, false);
        if (skillView != null) {
            skillView.setMinValue(property.getMinValue());
            skillView.setMaxValue(property.getMaxValue());
            int nameResId = ResourceHelper.from(getContext())
                    .getIdentifierForStringByNameParts("stat", property.getName());
            property.setNameResId(nameResId);
            skillView.setTitle(nameResId);
            int propValue = property.getValue() == 0 ? property.randomValue() : property.getValue();
            skillView.setIntValue(propValue);
            if (skillView != null) {
                mPrimaryList.addView(skillView);
                mSecondaryListObserver.onChanged();
            }
        }
    }

    private void populateSecondaryList() {
        setAdapter(new CharacterCardAdapter(getContext()));
        mSecondaryList.removeAllViews();
        if (mListAdapter != null && mSecondaryList != null) {
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View convertView = mSecondaryList.getChildAt(i);
                View child = mListAdapter.getView(i, convertView, mSecondaryList);
                if (child != null) {
                    if (child.getParent() == null) {
                        mSecondaryList.addView(child);
                    }
                }
            }
        }
    }

    public void setRelatedRetreiver(RelatedPropertesRetreiver relatedRetreiver) {
        mRelatedRetreiver = relatedRetreiver;
    }

}
