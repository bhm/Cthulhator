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
    public static final String METHOD = "0.4.1/";
    private Gender gender;
    private String seed;
    private int    results;

    protected RandomUserMEQuery(Options o) {
        this.gender = o.gender;
        this.seed = o.seed;
        this.results = o.results;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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
        if (!Gender.ANY.equals(gender)) {
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
        return METHOD;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (o == null || getClass() != o.getClass()) { return false; }

            Options options = (Options) o;

            if (results != options.results) { return false; }
            if (gender != options.gender) { return false; }
            if (seed != null ? !seed.equals(options.seed) : options.seed != null) { return false; }

            return true;
        }

        @Override
        public int hashCode() {
            int result = gender != null ? gender.hashCode() : 0;
            result = 31 * result + (seed != null ? seed.hashCode() : 0);
            result = 31 * result + results;
            return result;
        }
    }
}
