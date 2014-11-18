package com.bustiblelemons.cthulhator.character.characteristics.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.characteristics.logic.LoadCreatorCardsAsyn;
import com.bustiblelemons.cthulhator.character.characteristics.logic.OnCreatorCardsCreated;
import com.bustiblelemons.cthulhator.character.characteristics.logic.OnStatisitcsRandomized;
import com.bustiblelemons.cthulhator.character.characteristics.logic.RandomizeStatisitcsAsyn;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.character.creation.logic.CreatorCardsAdapter;
import com.bustiblelemons.cthulhator.character.creation.logic.RelatedPropertesRetreiver;
import com.bustiblelemons.cthulhator.character.creation.model.CreatorCard;
import com.bustiblelemons.cthulhator.character.creation.ui.AbsCharacterCreationActivity;
import com.bustiblelemons.cthulhator.system.CthulhuCharacter;
import com.bustiblelemons.cthulhator.system.edition.CthulhuEdition;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.cthulhator.view.characteristiccard.CharacteristicCard;
import com.bustiblelemons.logging.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by bhm on 31.08.14.
 */
public class StatisticsCreatorActivity extends AbsCharacterCreationActivity
        implements View.OnClickListener,
                   RelatedPropertesRetreiver, CharacteristicCard.OnPropertyChanged,
                   OnCreatorCardsCreated, OnStatisitcsRandomized {

    public static final  int    REQUEST_CODE = 4;
    private static final Logger log          = new Logger(StatisticsCreatorActivity.class);
    @Optional
    @InjectView(R.id.done)
    CircleButton mFab;
    @Optional
    @InjectView(R.id.recycler)
    RecyclerView mRecyclerView;

    private SavedCharacter             mSavedCharacter;
    private Toolbar                    mToolbar;
    private RecyclerView.LayoutManager mManager;
    private CreatorCardsAdapter        mCardsAdapter;
    private CthulhuEdition mEdition = CthulhuEdition.CoC5;

    private List<CreatorCard>         mCreatorCards;
    private Set<CharacterProperty>    mStatisticsSet;
    private RecyclerView.ItemAnimator mAnimator;

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
            initRecyclerViewer();
            if (mSavedCharacter == null) {
                mSavedCharacter = CthulhuCharacter.forEdition(mEdition);
                randomizStatsAsyn();
            } else {
                mStatisticsSet = mSavedCharacter.getStatistics();
                loadCardsAsync(mStatisticsSet);
            }
        }
    }

    private void initRecyclerViewer() {
        if (mManager == null) {
            mManager = new LinearLayoutManager(this);
        }
        mRecyclerView.setLayoutManager(mManager);
        if (mAnimator == null) {
            mAnimator = new DefaultItemAnimator();
        }
        mRecyclerView.setItemAnimator(mAnimator);
        mSavedCharacter = getInstanceArgument();
        mCardsAdapter = new CreatorCardsAdapter(this)
                .withRelatedPropertiesCallback(this)
                .withPropertyChangedCallback(this);
        mRecyclerView.setAdapter(mCardsAdapter);
    }

    private void loadCardsAsync(Set<CharacterProperty> statisticSet) {
        if (statisticSet != null && statisticSet.size() > 0) {
            LoadCreatorCardsAsyn loadAsyn = new LoadCreatorCardsAsyn(this);
            loadAsyn.withEdition(mEdition).withRelatedRetreiver(this)
                    .withCardsCreatedCallback(this);
            loadAsyn.executeCrossPlatform(statisticSet);
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
            if (mSavedCharacter != null) {
                randomizStatsAsyn();
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void randomizStatsAsyn() {
        RandomizeStatisitcsAsyn randomizeAsyn = new RandomizeStatisitcsAsyn(this)
                .withOnStatisticsRandomzied(this);
        randomizeAsyn.executeCrossPlatform(mSavedCharacter);
    }

    @OnClick(R.id.assign_skills)
    public void onOpenSkillsetEditor(View button) {
        launchSkillsetEditor(mSavedCharacter);
    }

    @OnClick(R.id.done)
    public void onDone(View button) {
        if (mSavedCharacter != null) {
            Collection<CharacterProperty> stats = getStatistics();
            mSavedCharacter.setPropertyValues(stats);
            setResult(RESULT_OK, mSavedCharacter);
            onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bundle e = data.getExtras();
            if (e != null && e.containsKey(INSTANCE_ARGUMENT)) {
                SavedCharacter passedBack = e.getParcelable(INSTANCE_ARGUMENT);
                log.d("passedback %s", passedBack);
                if (passedBack != null) {
                    onInstanceArgumentRead(passedBack);
                }
            }
        }
    }


    @Override
    protected void onInstanceArgumentRead(SavedCharacter arg) {
        mSavedCharacter = arg;
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

    @Override
    public void onPropertyChanged(CharacterProperty property) {
        if (mSavedCharacter != null) {
            mSavedCharacter.addCharacterProperty(property);
        }
    }

    public Collection<CharacterProperty> getStatistics() {
        List<CharacterProperty> r = new ArrayList<CharacterProperty>();
        if (mCreatorCards != null) {
            for (CreatorCard card : mCreatorCards) {
                if (card != null && card.getProperties() != null) {
                    r.addAll(card.getProperties());
                }
            }
        }
        return r;
    }

    @Override
    public void onCreatorCardsCreated(List<CreatorCard> cards) {
        if (mCardsAdapter == null) {
            mCardsAdapter = new CreatorCardsAdapter(this)
                    .withRelatedPropertiesCallback(this)
                    .withPropertyChangedCallback(this);
            mRecyclerView.setAdapter(mCardsAdapter);
        }
        mCardsAdapter.refreshData(cards);
    }

    @Override
    public void onStatisitcsRandomzied(SavedCharacter character) {
        mStatisticsSet = mSavedCharacter.getStatistics();
        loadCardsAsync(mStatisticsSet);
    }
}
