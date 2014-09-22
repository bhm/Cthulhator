package com.bustiblelemons.cthulhator.creation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacterTransformer;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterCard;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by bhm on 29.08.14.
 */
public class CreationWorkFlowActivity extends AbsCharacterCreationActivity
        implements View.OnClickListener {

    @InjectView(R.id.preview_card)
    CharacterCard characterCard;
    private SavedCharacter mSavedCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSavedCharacter = getInstanceArgument();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_workflow);
        ButterKnife.inject(this);
        onSetActionBarToClosable();
    }

    @OnClick({R.id.add_character_details,
            R.id.add_character_history,
            R.id.add_character_statistics,
            R.id.add_character_equipement})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_character_details:
                launchRandomCharacter(mSavedCharacter);
                break;
            case R.id.add_character_history:
                launchHistoryEditor(mSavedCharacter);
                break;
            case R.id.add_character_statistics:
                launchStatisticsCreator(mSavedCharacter);
                break;
            case R.id.add_character_equipement:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle e = data.getExtras();
        if (e != null && e.containsKey(INSTANCE_ARGUMENT)) {
            SavedCharacter passedBack = (SavedCharacter) e.getSerializable(INSTANCE_ARGUMENT);
            if (passedBack != null) {
                onInstanceArgumentRead(passedBack);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onInstanceArgumentRead(SavedCharacter arg) {
        mSavedCharacter = arg;
        if (mSavedCharacter != null && characterCard != null) {
            CharacterInfo characterInfo = SavedCharacterTransformer.transform(mSavedCharacter);
            characterCard.setCardInfo(characterInfo);
        }
    }
}
