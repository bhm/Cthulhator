package com.bustiblelemons.cthulhator.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.BaseFragment;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.views.SkillView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.github.scottmaclure.character.traits.model.RandomTraitsSet;

/**
 * Created by bhm on 02.08.14.
 */
public class CharacteristicSetFragment extends BaseFragment {

    public static final String SET = "set";
    @InjectView(R.id.hair)
    SkillView hair;
    @InjectView(R.id.facial)
    SkillView facial;
    @InjectView(R.id.characteristic_pager)
    SkillView characteristic;
    @InjectView(R.id.personality)
    SkillView personality;
    @InjectView(R.id.speech)
    SkillView speech;
    private RandomTraitsSet mRandomTraitsSet;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_random_trait_set, container, false);
        ButterKnife.inject(this, rootView);
        if (hasArgument(SET)) {
            mRandomTraitsSet = (RandomTraitsSet) getArguments().getSerializable(SET);
        } else if (savedInstanceState != null && savedInstanceState.containsKey(SET)) {
            mRandomTraitsSet = (RandomTraitsSet) savedInstanceState.getSerializable(SET);
        }
        loadTraitSet();
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SET, mRandomTraitsSet);
    }

    private void loadTraitSet() {
        if (mRandomTraitsSet != null) {
            hair.setValue(mRandomTraitsSet.getHair());
            facial.setValue(mRandomTraitsSet.getFacial());
            characteristic.setValue(mRandomTraitsSet.getCharacteristic());
            personality.setValue(mRandomTraitsSet.getPersonality());
            speech.setValue(mRandomTraitsSet.getSpeech());
        }
    }

    public static CharacteristicSetFragment newInstance(RandomTraitsSet set) {
        CharacteristicSetFragment r = new CharacteristicSetFragment();
        Bundle args = new Bundle();
        args.putSerializable(CharacteristicSetFragment.SET, set);
        r.setArguments(args);
        return r;
    }
}
