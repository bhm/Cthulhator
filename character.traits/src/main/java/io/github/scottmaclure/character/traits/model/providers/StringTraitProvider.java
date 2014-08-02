package io.github.scottmaclure.character.traits.model.providers;

/**
 * Created by bhm on 02.08.14.
 */
public class StringTraitProvider extends AbsTraitProvider<String> {

    private static StringTraitProvider instance;

    public static StringTraitProvider getInstance() {
        return instance == null ? instance = new StringTraitProvider() : instance;
    }

}
