package com.bustiblelemons.cthulhator.character.creation.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.persistance.CharacterWrapper;
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
    private CharacterWrapper        mCharacterWrapper;
    private CreationWorkflowAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_workflow);
        ButterKnife.inject(this);
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        mCharacterWrapper = getInstanceArgument();
        if (mCharacterWrapper == null) {
            mEdition = Settings.getEdition(this);
            mCharacterWrapper = CthulhuCharacter.forEdition(mEdition);
        } else {
            mEdition = mCharacterWrapper.getEdition();
        }
        setupAdapter();

    }


    @OnClick(R.id.done)
    public void onSaveCharacter(View view) {
        SavedCharactersProvider.saveCharacter(this, mCharacterWrapper);
        onBackPressed();
    }

    @Override
    protected void onInstanceArgumentRead(CharacterWrapper arg) {
        mCharacterWrapper = arg;
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
            launchRandomCharacter(mCharacterWrapper);
            break;
        case HISTORY:
            launchHistoryEditor(mCharacterWrapper);
            break;
        case STATS:
            launchStatisticsCreator(mCharacterWrapper);
            break;
        case EQUIPMENT:
            break;
        }
    }

    @Override
    public CharacterInfo onRetreiveCharacterInfo(Context arg) {
        return mCharacterWrapper.onRetreiveCharacterInfo(arg);
    }
}
