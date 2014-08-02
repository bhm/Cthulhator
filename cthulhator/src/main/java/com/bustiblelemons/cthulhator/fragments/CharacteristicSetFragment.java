package com.bustiblelemons.cthulhator.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.views.SkillView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.github.scottmaclure.character.traits.model.RandomTraitsSet;

/**
 * Created by bhm on 02.08.14.
 */
public class CharacteristicSetFragment extends AbsArgFragment<RandomTraitsSet> {

    @InjectView(R.id.hair)
    SkillView hair;
    @InjectView(R.id.facial)
    SkillView facial;
    @InjectView(R.id.characteristic)
    SkillView characteristic;
    @InjectView(R.id.personality)
    SkillView personality;
    @InjectView(R.id.speech)
    SkillView speech;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_random_trait_set, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    protected void onInstanceArgumentRead(RandomTraitsSet instanceArgument) {
        loadTraitSet(instanceArgument);
    }


    private void loadTraitSet(RandomTraitsSet traitsSet) {
        if (traitsSet != null) {
            hair.setValue(traitsSet.getHair());
            facial.setValue(traitsSet.getFacial());
            characteristic.setValue(traitsSet.getCharacteristic());
            personality.setValue(traitsSet.getPersonality());
            speech.setValue(traitsSet.getSpeech());
        }
    }

    public static CharacteristicSetFragment newInstance(RandomTraitsSet set) {
        CharacteristicSetFragment r = new CharacteristicSetFragment();
        r.setNewInstanceArgument(set);
        return r;
    }
}
