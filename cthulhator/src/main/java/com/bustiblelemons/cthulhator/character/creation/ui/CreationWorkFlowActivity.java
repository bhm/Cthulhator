package com.bustiblelemons.cthulhator.character.creation.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.persistance.CharacterWrappper;
import com.bustiblelemons.cthulhator.character.persistance.SavedCharactersProvider;
import com.bustiblelemons.cthulhator.character.skills.logic.CreationWorkflowAdapter;
import com.bustiblelemons.cthulhator.character.skills.logic.OnCreationWorkflowListener;
import com.bustiblelemons.cthulhator.character.skills.model.CreationWorkflowCard;
import com.bustiblelemons.cthulhator.settings.Settings;
import com.bustiblelemons.cthulhator.system.CthulhuCharacter;
import com.bustiblelemons.cthulhator.system.edition.GameEdition;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfoProvider;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by bhm on 29.08.14.
 */
public class CreationWorkFlowActivity extends AbsCharacterCreationActivity
        implements OnCreationWorkflowListener, CharacterInfoProvider {

    @Optional
    @InjectView(R.id.recycler)
    RecyclerView mRecyclerView;

    private GameEdition mEdition = GameEdition.CoC5;
    private CharacterWrappper       mCharacterWrappper;
    private CreationWorkflowAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_workflow);
        ButterKnife.inject(this);
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        mCharacterWrappper = getInstanceArgument();
        if (mCharacterWrappper == null) {
            mEdition = Settings.getEdition(this);
            mCharacterWrappper = CthulhuCharacter.forEdition(mEdition);
        } else {
            mEdition = mCharacterWrappper.getEdition();
        }
        setupAdapter();

    }


    @OnClick(R.id.done)
    public void onSaveCharacter(View view) {
        SavedCharactersProvider.saveCharacter(this, mCharacterWrappper);
        onBackPressed();
    }

    @Override
    protected void onInstanceArgumentRead(CharacterWrappper arg) {
        mCharacterWrappper = arg;
        setupAdapter();
    }

    private void setupAdapter() {
        if (mRecyclerAdapter == null) {
            mRecyclerAdapter = new CreationWorkflowAdapter();
            mRecyclerView.setAdapter(mRecyclerAdapter);
        }
        mRecyclerAdapter.refreshData(CreationWorkflowCard.values());
        mRecyclerAdapter.withCharacterInfoProvider(this)
                .withCreationWorkflowCardListener(this);
    }

    @Override
    public void onCreationWorkflowClicked(CreationWorkflowCard card) {
        switch (card) {
        case DETAILS:
            launchRandomCharacter(mCharacterWrappper);
            break;
        case HISTORY:
            launchHistoryEditor(mCharacterWrappper);
            break;
        case STATS:
            launchStatisticsCreator(mCharacterWrappper);
            break;
        case EQUIPMENT:
            break;
        }
    }

    @Override
    public CharacterInfo onRetreiveCharacterInfo(Context arg) {
        return mCharacterWrappper.onRetreiveCharacterInfo(arg);
    }
}
