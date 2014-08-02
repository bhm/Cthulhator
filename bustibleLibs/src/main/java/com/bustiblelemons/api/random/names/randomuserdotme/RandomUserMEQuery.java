package com.bustiblelemons.api.random.names.randomuserdotme;

import com.bustiblelemons.api.AbsJacksonQuery;
import com.bustiblelemons.api.model.Gender;
import com.bustiblelemons.api.random.names.randomuserdotme.model.RandomUserDotMe;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by bhm on 25.07.14.
 */
public class RandomUserMEQuery extends AbsJacksonQuery<RandomUserDotMe> {

    public static final String HOST    = "api.randomuser.me";
    public static final String RESULTS = "results";
    public static final String SEED    = "seed";
    public static final String GENDER  = "gender";
    private Gender gender;
    private String seed;
    private int    results;


    protected RandomUserMEQuery(Options o) {
        this.gender = o.gender;
        this.seed = o.seed;
        this.results = o.results;
    }

    @Override
    public String getHost() {
        return HOST;
    }

    @Override
    protected List<NameValuePair> getNameValuePairs() {
        List<NameValuePair> r = new ArrayList<NameValuePair>();
        r.add(new BasicNameValuePair(RESULTS, this.results + ""));
        if (seed != null) {
            r.add(new BasicNameValuePair(SEED, this.seed));
        }
        if (gender != null) {
            String g = this.gender.name().toLowerCase(Locale.ENGLISH);
            r.add(new BasicNameValuePair(GENDER, g));
        }
        return r;
    }

    @Override
    protected boolean usesSSL() {
        return false;
    }

    @Override
    public String getMethod() {
        return null;
    }

    public static class Options {
        private Gender gender;
        private String seed;
        private int results = 4;

        public Options setGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Options setSeed(String seed) {
            this.seed = seed;
            return this;
        }

        public Options setResults(int results) {
            this.results = results;
            return this;
        }

        public RandomUserMEQuery build() {
            return new RandomUserMEQuery(this);
        }

    }
}
