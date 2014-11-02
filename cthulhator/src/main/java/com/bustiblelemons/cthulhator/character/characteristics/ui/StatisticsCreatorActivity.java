package com.bustiblelemons.cthulhator.character.characteristics.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.characteristics.logic.CharacterPropertyAdapter;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.character.creation.logic.CreatorAdapter;
import com.bustiblelemons.cthulhator.character.creation.logic.CreatorCardFactory;
import com.bustiblelemons.cthulhator.character.creation.logic.RelatedPropertesRetreiver;
import com.bustiblelemons.cthulhator.character.creation.model.CreatorCard;
import com.bustiblelemons.cthulhator.character.creation.ui.AbsCharacterCreationActivity;
import com.bustiblelemons.cthulhator.system.CthulhuCharacter;
import com.bustiblelemons.cthulhator.system.edition.CthulhuEdition;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.logging.Logger;
import com.bustiblelemons.observablescrollview.ObservableScrollView;
import com.bustiblelemons.views.SkillView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by bhm on 31.08.14.
 */
public class StatisticsCreatorActivity extends AbsCharacterCreationActivity
        implements SkillView.OnValueButtonsClicked, View.OnClickListener,
                   RelatedPropertesRetreiver {

    public static final  int    REQUEST_CODE = 4;
    private static final Logger log          = new Logger(StatisticsCreatorActivity.class);
    @Optional
    @InjectView(R.id.done)
    CircleButton         mFab;
    @Optional
    @InjectViews({R.id.edu,
            R.id.intelligence,
            R.id.pow,
            R.id.dex,
            R.id.app,
            R.id.str, R.id.con, R.id.siz})
    List<SkillView>      mCharacteristicsViewList;
    @Optional
    @InjectView(R.id.scroll)
    ObservableScrollView mScrollView;
    @Optional
    @InjectView(R.id.recycler)
    RecyclerView         mRecyclerView;

    private SparseArray<CharacterProperty>        mIdsToProperty = new SparseArray<CharacterProperty>();
    private SparseArray<CharacterPropertyAdapter> mIdsToAdapters = new SparseArray<CharacterPropertyAdapter>();
    private SavedCharacter             mSavedCharacter;
    private Toolbar                    mToolbar;
    private RecyclerView.LayoutManager mManager;
    private CreatorAdapter             mRecyclerAdapter;
    private CthulhuEdition mEdition = CthulhuEdition.CoC5;
    private List<CreatorCard> mCreatorCards;

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
        if (mRecyclerView != null) {
            mManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mManager);
            mRecyclerAdapter = new CreatorAdapter();
            mRecyclerView.setAdapter(mRecyclerAdapter);
            mSavedCharacter = getInstanceArgument();
            if (mSavedCharacter == null) {
                mSavedCharacter = CthulhuCharacter.forEdition(mEdition);
            }
            mCreatorCards = CreatorCardFactory.getCardsFrom(mEdition, this, mSavedCharacter.getStatistics());
            mRecyclerAdapter.refreshData(mCreatorCards);
        }
    }

    private void setupScrollView() {
        if (mSavedCharacter == null) {
            mSavedCharacter = CthulhuCharacter.forEdition(CthulhuEdition.CoC5);
            fillPropertyViews();
        } else if (mSavedCharacter != null && !mSavedCharacter.hasAssignedStatistics()) {
            distributeRandomPoints();
        } else {
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

    @Override
    public Collection<CharacterProperty> getRelatedPropertes(CharacterProperty property) {
        if (property != null) {
            return mSavedCharacter.getRelatedProperties(property);
        }
        return Collections.emptyList();
    }
}
