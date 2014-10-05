package com.bustiblelemons.cthulhator.adapters;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.bustiblelemons.adapters.viewpager.AbsFragmentPagerAdapter;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.test.BRPCharacterFragment;

/**
 * Created by bhm on 26.07.14.
 */
public class BRPCharacterPagerAdapter
        extends AbsFragmentPagerAdapter<SavedCharacter, BRPCharacterFragment> {
    public BRPCharacterPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public BRPCharacterFragment newInstance(SavedCharacter item) {
        return BRPCharacterFragment.newInstance(new Bundle());
    }
}
