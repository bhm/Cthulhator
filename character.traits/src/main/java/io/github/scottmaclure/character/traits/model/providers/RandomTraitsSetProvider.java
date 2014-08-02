package io.github.scottmaclure.character.traits.model.providers;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.scottmaclure.character.traits.model.RandomTraitsSet;
import io.github.scottmaclure.character.traits.model.TraitsSet;

/**
 * Created by bhm on 02.08.14.
 */
public class RandomTraitsSetProvider {
    private final TraitsSet mSet;
    private final Random    mRandom;

    private RandomTraitsSetProvider(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mSet = mapper.readValue(new FileInputStream(file), TraitsSet.class);
        mRandom = new Random();
    }

    private static RandomTraitsSetProvider instance;

    public static RandomTraitsSetProvider getInstance(File file) throws IOException {
        return instance == null ? instance = new RandomTraitsSetProvider(file) : instance;
    }

    public RandomTraitsSet getRandomTraitsSet() {
        return RandomTraitsSet.from(mSet);
    }

    public List<RandomTraitsSet> getRandomTraitSets(int total) {
        List<RandomTraitsSet> r = new ArrayList<RandomTraitsSet>();
        for (int i = 0; i < total; i++) {
            r.add(getRandomTraitsSet());
        }
        return r;
    }

}