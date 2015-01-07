package com.bustiblelemons.cthulhator.character.creation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.characterslist.logic.SavedCharacterTransformer;
import com.bustiblelemons.cthulhator.character.persistance.CharacterWrappper;
import com.bustiblelemons.cthulhator.character.persistance.SavedCharactersProvider;
import com.bustiblelemons.cthulhator.settings.Settings;
import com.bustiblelemons.cthulhator.system.CthulhuCharacter;
import com.bustiblelemons.cthulhator.system.edition.GameEdition;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterCardView;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfo;
import com.bustiblelemons.views.card.CardView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by bhm on 29.08.14.
 */
public class CreationWorkFlowActivity extends AbsCharacterCreationActivity
        implements View.OnClickListener, CharacterCardView.OnCharacterCardViewClick,
                   CardView.OnTitleClick {

    @InjectView(R.id.preview_card)
    CharacterCardView mCharacterCardView;
    @InjectView(R.id.add_character_equipement)
    CardView          mEquipmentCardView;
    private CharacterWrappper mCharacterWrappper;
    private GameEdition mEdition = GameEdition.CoC5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_workflow);
        View addDetails = findViewById(R.id.add_character_details);
        if (addDetails != null) {
            addDetails.setOnClickListener(this);
        }
        ButterKnife.inject(this);
        mCharacterWrappper = getInstanceArgument();
        if (mCharacterWrappper == null) {
            mEdition = Settings.getEdition(this);
            mCharacterWrappper = CthulhuCharacter.forEdition(mEdition);
        } else {
            mEdition = mCharacterWrappper.getEdition();
        }
        if (mCharacterCardView != null) {
            mCharacterCardView.setOnCharacterCardViewClick(this);
        }
        if (mEquipmentCardView != null) {
            mEquipmentCardView.setOnTitleClick(this);
        }
    }

    @OnClick({R.id.add_character_details,
            R.id.add_character_history,
            R.id.add_character_statistics,
            R.id.add_character_equipement})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.add_character_details:
            launchRandomCharacter(mCharacterWrappper);
            break;
        case R.id.add_character_history:
            launchHistoryEditor(mCharacterWrappper);
            break;
        case R.id.add_character_statistics:
            launchStatisticsCreator(mCharacterWrappper);
            break;
        case R.id.add_character_equipement:
            break;
        }
    }

    @OnClick(R.id.done)
    public void onSaveCharacter(View view) {
        SavedCharactersProvider.saveCharacter(this, mCharacterWrappper);
        onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bundle e = data.getExtras();
            if (e != null && e.containsKey(INSTANCE_ARGUMENT)) {
                CharacterWrappper passedBack = e.getParcelable(INSTANCE_ARGUMENT);
                log.d("passedback %s", passedBack);
                if (passedBack != null) {
                    onInstanceArgumentRead(passedBack);
                }
            }
        }
    }

    @Override
    protected void onInstanceArgumentRead(CharacterWrappper arg) {
        mCharacterWrappper = arg;
        if (mCharacterWrappper != null && mCharacterCardView != null) {
            CharacterInfo characterInfo = SavedCharacterTransformer.getInstance()
                    .withContext(this)
                    .transform(mCharacterWrappper);
            mCharacterCardView.setCardInfo(characterInfo);
        }
    }

    @Override
    public void onImageClick(CharacterCardView view) {
        launchRandomCharacter(mCharacterWrappper);
    }

    @Override
    public void onMainInfoClick(CharacterCardView view) {
        launchRandomCharacter(mCharacterWrappper);
    }

    @Override
    public void onShortInfoClick(CharacterCardView view) {
        launchRandomCharacter(mCharacterWrappper);
    }

    @Override
    public void onExtraInfoClick(CharacterCardView view) {
        launchStatisticsCreator(mCharacterWrappper);
    }

    @Override
    public void onCardTitleClick(CardView cardView, TextView view) {
        Toast.makeText(this, view.getText(), Toast.LENGTH_LONG).show();
    }
}
