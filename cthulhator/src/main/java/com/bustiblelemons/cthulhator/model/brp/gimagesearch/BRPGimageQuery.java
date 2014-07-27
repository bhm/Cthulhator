package com.bustiblelemons.cthulhator.model.brp.gimagesearch;

import java.util.Locale;

/**
 * Created by bhm on 27.07.14.
 */
public class BRPGimageQuery {
    private static final String             JAZZ_AGE_YEAR = "1920";
    private static final String             DEFAULT_YEAR  = JAZZ_AGE_YEAR;
    private static final String             GASLIGHT_YEAR = "1890";
    private              String             mYear         = DEFAULT_YEAR;
    private              GImageSearchGender mGender       = GImageSearchGender.ANY;
    private              String             mType         = "portrait";

    public String year(String year) {
        this.mYear = year;
        return getQuery();
    }

    public String gender(GImageSearchGender gender) {
        this.mGender = gender;
        return getQuery();
    }

    public String type(String type) {
        this.mType = type;
        return getQuery();
    }

    public String getQuery() {
        StringBuilder r = new StringBuilder(mYear);
        if (!mGender.equals(GImageSearchGender.ANY)) {
            r.append("+").append(mGender.name().toLowerCase(Locale.ENGLISH));
        }
        if (mType != null) {
            r.append("+").append(mType);
        }
        return r.toString();
    }
}
