package io.github.scottmaclure.character.traits.model.providers;

import java.util.List;
import java.util.Random;

/**
 * Created by bhm on 02.08.14.
 */
public abstract class AbsTraitProvider<T> {
    private Random mRandom = new Random();

    public T getRandomTrait(List<T> list) {
        if (list != null) {
            int max = list.size();
            int randPos =  mRandom.nextInt(max);
            return list.get(randPos);
        }
        return null;
    }
}
