package com.bustiblelemons.cthulhator.character.viewer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.bustiblelemons.activities.AbsArgActivity;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.character.description.model.CharacterDescription;
import com.bustiblelemons.cthulhator.character.portrait.model.Portrait;
import com.bustiblelemons.cthulhator.character.portrait.ui.PortraitsActivity;
import com.bustiblelemons.cthulhator.character.viewer.logic.OnExpandCharacterViewer;
import com.bustiblelemons.cthulhator.character.viewer.ui.CharacterViewerFragment;
import com.bustiblelemons.cthulhator.test.BRPCharacterFragment;
import com.bustiblelemons.views.loadingimage.RemoteImage;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by bhm on 20.07.14.
 */
public class CharacterViewerActivity extends AbsArgActivity<SavedCharacter>
        implements BRPCharacterFragment.BRPCharacterListener,
                   ImageChooserListener,
                   OnExpandCharacterViewer {

    public static final String CHARCTER_ID       = "character_id";
    private static      int    sTransparentColor = Color.TRANSPARENT;
    private static      int    sSolidColor       = Color.TRANSPARENT;
    @InjectView(android.R.id.icon)
    RemoteImage mPortraitView;
    @InjectView(R.id.header)
    Toolbar     mToolbar;
    private SavedCharacter          mSavedCharacter;
    private CharacterViewerFragment mCharacterViewerFragment;
    private Portrait                mMainPortrait;
    private CharacterDescription    mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (sSolidColor == Color.TRANSPARENT) {
            sSolidColor = getResources().getColor(R.color.solid_background);
        }
        setContentView(R.layout.activity_character_viewer);
        ButterKnife.inject(this);
        mSavedCharacter = getInstanceArgument();
        attachCharacterViewer();
        loadIcon();
    }

    private void attachCharacterViewer() {
        if (mCharacterViewerFragment == null) {
            mCharacterViewerFragment = CharacterViewerFragment.newInstance(mSavedCharacter);
        }
        addFragment(android.R.id.content, mCharacterViewerFragment);
    }

    @Override
    protected void onInstanceArgumentRead(SavedCharacter arg) {
        if (arg != null) {
            mSavedCharacter = arg;
            loadIcon();
            attachCharacterViewer();
        }
    }

    private void loadIcon() {
        if (mSavedCharacter != null && mPortraitView != null) {
            mMainPortrait = mSavedCharacter.getMainPortrait();
            if (mMainPortrait != null) {
                mPortraitView.loadFrom(mMainPortrait.getUrl());
            }
        }
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

    @Optional
    @OnClick(R.id.go_back)
    public void onGoBack() {
        onBackPressed();
    }


    @Override
    public void onError(String s) {

    }

    @Override
    public void onExpandCharacterViewer() {

    }

    @Override
    public void onCollapseCharacterViewer() {

    }

    @Override
    public void onFinishExpandAnimation() {
        mDescription = mSavedCharacter.getDescription();
        if (mToolbar != null && mDescription != null && mDescription.getName() != null) {
            mToolbar.setTitle(mDescription.getName().getFullName());
            mToolbar.setBackgroundColor(sSolidColor);
        }
    }

    @Override
    public void onFinishCollapseAnimation() {

        if (mToolbar != null) {
            mToolbar.setTitle(null);
            mToolbar.setBackgroundColor(sTransparentColor);
        }
    }
}