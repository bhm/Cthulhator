package com.bustiblelemons.cthulhator.character.characteristics.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.characteristics.logic.CharacterPropertyAdapter;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.character.creation.ui.AbsCharacterCreationActivity;
import com.bustiblelemons.cthulhator.system.CthulhuCharacter;
import com.bustiblelemons.cthulhator.system.edition.CthulhuEdition;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.cthulhator.system.properties.ObservableCharacterProperty;
import com.bustiblelemons.logging.Logger;
import com.bustiblelemons.observablescrollview.ObservableScrollView;
import com.bustiblelemons.views.SkillView;

import java.util.List;
import java.util.Set;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

/**
 * Created by bhm on 31.08.14.
 */
public class StatisticsCreatorActivity extends AbsCharacterCreationActivity
        implements SkillView.OnValueButtonsClicked, View.OnClickListener,
                   ObservableCharacterProperty.OnReltivesChanged<CharacterProperty> {

    public static final  int    REQUEST_CODE = 4;
    private static final Logger log          = new Logger(StatisticsCreatorActivity.class);
    @InjectView(R.id.done)
    CircleButton         mFab;
    @InjectViews({R.id.edu,
            R.id.intelligence,
            R.id.pow,
            R.id.dex,
            R.id.app,
            R.id.str, R.id.con, R.id.siz})
    List<SkillView>      mCharacteristicsViewList;
    @InjectView(R.id.scroll)
    ObservableScrollView mScrollView;
    private SparseArray<CharacterProperty>        mIdsToProperty = new SparseArray<CharacterProperty>();
    private SparseArray<CharacterPropertyAdapter> mIdsToAdapters = new SparseArray<CharacterPropertyAdapter>();
    private SavedCharacter mSavedCharacter;
    private Toolbar        mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_creator);
        mToolbar = (Toolbar) findViewById(R.id.header);
        if (mToolbar != null) {
            mToolbar.setNavigationOnClickListener(this);
            setSupportActionBar(mToolbar);
        }
        ButterKnife.inject(this);
        mSavedCharacter = getInstanceArgument();
        if (mSavedCharacter == null) {
            mSavedCharacter = CthulhuCharacter.forEdition(CthulhuEdition.CoC5);
            mSavedCharacter.setPropertiesObserver(this);
            fillPropertyViews();
        } else if (mSavedCharacter != null && !mSavedCharacter.hasAssignedStatistics()) {
            mSavedCharacter.setPropertiesObserver(this);
            distributeRandomPoints();
        } else {
            mSavedCharacter.setPropertiesObserver(this);
            fillPropertyViews();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.statistics_creator, menu);
        return menu.size() > 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == R.id.reroll) {
            distributeRandomPoints();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.assign_skills)
    public void onOpenSkillsetEditor(View button) {
        launchSkillsetEditor(mSavedCharacter);
    }

    @OnClick(R.id.done)
    public void onDone(View button) {
        setResult(RESULT_OK, mSavedCharacter);
        onBackPressed();
    }


    @Override
    protected void onInstanceArgumentRead(SavedCharacter arg) {
        mSavedCharacter = arg;
    }

    private void distributeRandomPoints() {
        if (mCharacteristicsViewList == null) {
            return;
        }
        for (SkillView view : mCharacteristicsViewList) {
            if (view != null && view.getTag() != null) {
                String tag = (String) view.getTag();
                CharacterProperty property = getProperty(tag);
                int id = view.getId();
                if (property != null) {
                    mIdsToProperty.put(id, property);
                    view.setOnValueButtonsClicked(this);
                    view.setMinValue(property.getMinValue());
                    view.setMaxValue(property.getMaxValue());
                    int randValue = property.randomValue();
                    view.setIntValue(randValue);

                }
            }
        }
        readRelations();
    }

    private void fillPropertyViews() {
        if (mCharacteristicsViewList == null) {
            return;
        }
        for (SkillView view : mCharacteristicsViewList) {
            if (view != null && view.getTag() != null) {
                String tag = (String) view.getTag();
                CharacterProperty property = getProperty(tag);
                int id = view.getId();
                if (property != null) {
                    view.setOnValueButtonsClicked(this);
                    view.setMinValue(property.getMinValue());
                    view.setMaxValue(property.getMaxValue());
                    int value = property.getValue();
                    view.setIntValue(value);
                    mIdsToProperty.put(id, property);
                }
            }
        }
        readRelations();
    }

    private void readRelations() {
        for (SkillView view : mCharacteristicsViewList) {
            String tag = (String) view.getTag();
            CharacterProperty property = getProperty(tag);
            int id = view.getId();
            if (property.hasRelations()) {
                CharacterPropertyAdapter adapter;
                if (mIdsToAdapters.get(id) != null) {
                    adapter = mIdsToAdapters.get(id);
                } else {
                    adapter = new CharacterPropertyAdapter(this);
                    mIdsToAdapters.put(id, adapter);
                }
                adapter.refreshData(mSavedCharacter.getRelatedProperties(property));
                view.setAdapter(adapter);
            }
        }
    }

    private CharacterProperty getProperty(String name) {
        for (CharacterProperty property : mSavedCharacter.getStatistics()) {
            if (property.getName() != null && property.getName().equals(name)) {
                log.d("%s vs %s", name, property.getName());
                return property;
            }
        }
        return null;
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
        int id = view.getId();
        CharacterProperty property = mIdsToProperty.get(id);
        view.setIntValue(property.getValue());
        property.notifyCorelatives();
        if (property != null && property.increaseValue()) {
            updateView(view, id, property);
        }
    }

    private void decreasePropertyAndUpdateView(SkillView view) {
        int id = view.getId();
        CharacterProperty property = mIdsToProperty.get(id);
        view.setIntValue(property.getValue());
        property.notifyCorelatives();
        if (property != null && property.decreaseValue()) {
            updateView(view, id, property);
        }
    }


    /**
     * Redo this into one custom widget Multi CharacterProperty with Adaptergenerated character property list
     *
     * @param ofProperty
     */
    @Override
    public void onUpdateRelativeProperties(CharacterProperty ofProperty) {
        if (ofProperty == null) {
            return;
        }
        SkillView skillView = null;
        int id = -1;
        String propName = ofProperty.getName();
        for (SkillView view : mCharacteristicsViewList) {
            if (view != null) {
                String tag = (String) view.getTag();
                if (tag.equals(propName)) {
                    id = view.getId();
                    break;
                }
            }
        }
        if (skillView != null && id > 0) {
            updateView(skillView, id, ofProperty);
        }
    }

    private void updateView(SkillView view, int id, CharacterProperty property) {
        CharacterPropertyAdapter adapter = mIdsToAdapters.get(id);
        if (adapter == null) {
            adapter = new CharacterPropertyAdapter(this);
        }
        Set<CharacterProperty> data = mSavedCharacter.getRelatedProperties(property);
        adapter.refreshData(data);
        view.setAdapter(adapter);
        mIdsToProperty.put(id, property);
        mIdsToAdapters.put(id, adapter);
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
