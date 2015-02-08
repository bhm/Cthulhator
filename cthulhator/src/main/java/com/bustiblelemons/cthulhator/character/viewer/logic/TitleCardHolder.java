package com.bustiblelemons.cthulhator.character.viewer.logic;

import android.view.View;

import com.bustiblelemons.cthulhator.character.viewer.CharacterViewerCard;
import com.bustiblelemons.cthulhator.view.charactercard.CharacterInfoProvider;

/**
 * Created by hiv on 08.02.15.
 */
public class TitleCardHolder extends CharacterViewerCardHolder {
    private CharacterInfoProvider mCharacterInfoProvider;

    public TitleCardHolder(View view) {
        super(view);
    }

    public TitleCardHolder withCharacterInfoProivder(CharacterInfoProvider proivder) {
        mCharacterInfoProvider = proivder;
        return this;
    }

    @Override
    public void onBindData(CharacterViewerCard item) {

    }
}
