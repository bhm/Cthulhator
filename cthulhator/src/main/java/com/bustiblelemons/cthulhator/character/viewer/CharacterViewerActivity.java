package com.bustiblelemons.cthulhator.character.viewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.bustiblelemons.activities.AbsArgActivity;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.character.portrait.ui.PortraitsActivity;
import com.bustiblelemons.cthulhator.test.BRPCharacterFragment;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;

/**
 * Created by bhm on 20.07.14.
 */
public class CharacterViewerActivity extends AbsArgActivity<SavedCharacter>
        implements BRPCharacterFragment.BRPCharacterListener,
                   ImageChooserListener {

    public static final String CHARCTER_ID = "character_id";
    private BRPCharacterFragment brpCharacterFragment;
    private SavedCharacter       mSavedCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_viewer);
        attachCharacterViewer();
        getSupportActionBar();
    }

    @Override
    protected void onInstanceArgumentRead(SavedCharacter arg) {
        mSavedCharacter = arg;
    }

    private void attachCharacterViewer() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (brpCharacterFragment == null) {
            brpCharacterFragment = BRPCharacterFragment.newInstance(getIntent().getExtras());
        }
        transaction.add(android.R.id.content, brpCharacterFragment);
        transaction.commit();
    }

    @Override
    public boolean onPickPicture(int characterId) {
        Intent i = new Intent(this, PortraitsActivity.class);
        startActivityForResult(i, characterId);
        return false;
    }

    @Override
    public void onImageChosen(ChosenImage chosenImage) {

    }

    @Override
    public void onError(String s) {

    }
}
