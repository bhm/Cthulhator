package io.github.scottmaclure.character.traits.network.api;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import io.github.scottmaclure.character.traits.model.TraitsSet;


/**
 * Created by bhm on 01.08.14.
 */
public class CharacterTraitsQuery extends AbsJacksonQuery<TraitsSet> {

    public static final String METHOD = "character-traits/traits-default.json";
    public static final String HOST   = "scottmaclure.github.io";

    @Override
    protected List<NameValuePair> getNameValuePairs() {
        return new ArrayList<NameValuePair>();
    }

    @Override
    protected boolean usesSSL() {
        return false;
    }

    @Override
    public String getHost() {
        return HOST;
    }

    @Override
    public String getMethod() {
        return METHOD;
    }
}
