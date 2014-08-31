package com.bustiblelemons.cthulhator.adapters;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.bustiblelemons.adapters.viewpager.AbsFragmentPagerAdapter;
import com.bustiblelemons.cthulhator.fragments.BRPCharacterFragment;
import com.bustiblelemons.cthulhator.model.brp.AbsBRPCharacter;

/**
 * Created by bhm on 26.07.14.
 */
public class BRPCharacterPagerAdapter extends AbsFragmentPagerAdapter<AbsBRPCharacter, BRPCharacterFragment> {
    public BRPCharacterPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public BRPCharacterFragment newInstance(AbsBRPCharacter item) {
        return BRPCharacterFragment.newInstance(new Bundle());
    }
}
