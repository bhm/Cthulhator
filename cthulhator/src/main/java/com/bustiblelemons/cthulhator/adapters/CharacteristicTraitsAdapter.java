package com.bustiblelemons.cthulhator.adapters;

import android.support.v4.app.FragmentManager;

import com.bustiblelemons.adapters.viewpager.AbsFragmentPagerAdapter;
import com.bustiblelemons.cthulhator.fragments.CharacteristicSetFragment;

import io.github.scottmaclure.character.traits.model.RandomTraitsSet;

/**
 * Created by bhm on 02.08.14.
 */
public class CharacteristicTraitsAdapter
        extends AbsFragmentPagerAdapter<RandomTraitsSet, CharacteristicSetFragment> {
    public CharacteristicTraitsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharacteristicSetFragment newInstance(RandomTraitsSet item) {
        return CharacteristicSetFragment.newInstance(item);
    }
}
