package com.bustiblelemons.google.apis;

import com.bustiblelemons.api.model.Gender;

/**
 * Created by bhm on 24.09.14.
 */
public class GenderTransformer {
    public static Gender toRandomUserMe(GoogleSearchGender gender) {
        if (gender == null) {
            return null;
        }
        if (GoogleSearchGender.ANY.equals(gender)) {
            return Gender.ANY;
        } else {
            return gender.isMale() ? Gender.MALE : Gender.FEMALE;
        }
    }
}
