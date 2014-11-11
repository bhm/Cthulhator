package com.bustiblelemons.cthulhator.character.characteristics.ui;

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
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.character.creation.logic.CreatorAdapter;
import com.bustiblelemons.cthulhator.character.creation.logic.CreatorCardFactory;
import com.bustiblelemons.cthulhator.character.creation.logic.RelatedPropertesRetreiver;
import com.bustiblelemons.cthulhator.character.creation.model.CreatorCard;
import com.bustiblelemons.cthulhator.character.creation.ui.AbsCharacterCreationActivity;
import com.bustiblelemons.cthulhator.system.CthulhuCharacter;
import com.bustiblelemons.cthulhator.system.edition.CthulhuEdition;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;
import com.bustiblelemons.cthulhator.view.characteristiccard.CharacteristicCard;
import com.bustiblelemons.logging.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
                   RelatedPropertesRetreiver, CharacteristicCard.OnPropertyChanged {

    public static final  int    REQUEST_CODE = 4;
    private static final Logger log          = new Logger(StatisticsCreatorActivity.class);
    @Optional
    @InjectView(R.id.done)
    CircleButton         mFab;
    @Optional
    @InjectView(R.id.recycler)
    RecyclerView         mRecyclerView;

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

            mSavedCharacter = getInstanceArgument();
            if (mSavedCharacter == null) {
                mSavedCharacter = CthulhuCharacter.forEdition(mEdition);
            }
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mCreatorCards = CreatorCardFactory.getCardsFrom(mEdition, this, mSavedCharacter.getStatistics());
            mRecyclerAdapter = new CreatorAdapter(mCreatorCards, this);
            mRecyclerView.setAdapter(mRecyclerAdapter);
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
        mSavedCharacter.addCharacterProperty(property);
    }
}
