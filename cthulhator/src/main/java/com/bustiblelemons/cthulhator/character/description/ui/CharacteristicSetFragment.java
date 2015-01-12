package com.bustiblelemons.cthulhator.character.description.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.fragments.AbsFragmentWithSerializable;
import com.micromobs.android.floatlabel.FloatLabelEditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.github.scottmaclure.character.traits.model.RandomTraitsSet;

/**
 * Created by bhm on 02.08.14.
 */
public class CharacteristicSetFragment extends AbsFragmentWithSerializable<RandomTraitsSet> {

    @InjectView(R.id.hair)
    FloatLabelEditText hair;
    @InjectView(R.id.facial)
    FloatLabelEditText facial;
    @InjectView(R.id.characteristic)
    FloatLabelEditText characteristic;
    @InjectView(R.id.personality)
    FloatLabelEditText personality;
    @InjectView(R.id.speech)
    FloatLabelEditText speech;

    public static CharacteristicSetFragment newInstance(RandomTraitsSet set) {
        CharacteristicSetFragment r = new CharacteristicSetFragment();
        r.setNewInstanceArgument(set);
        return r;
    }

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
            hair.setText(traitsSet.getHair());
            facial.setText(traitsSet.getFacial());
            characteristic.setText(traitsSet.getCharacteristic());
            personality.setText(traitsSet.getPersonality());
            speech.setText(traitsSet.getSpeech());
        }
    }
}
