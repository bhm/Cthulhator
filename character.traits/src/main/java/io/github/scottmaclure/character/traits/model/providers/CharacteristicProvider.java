package io.github.scottmaclure.character.traits.model.providers;

import io.github.scottmaclure.character.traits.model.Characteristic;

/**
 * Created by bhm on 02.08.14.
 */
public class CharacteristicProvider extends AbsTraitProvider<Characteristic> {

    private static CharacteristicProvider instance;

    public static CharacteristicProvider getInstance() {
        return instance == null ? instance = new CharacteristicProvider() : instance;
    }
}
