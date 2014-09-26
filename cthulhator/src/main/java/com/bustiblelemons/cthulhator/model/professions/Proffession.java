package com.bustiblelemons.cthulhator.model.professions;

import com.bustiblelemons.cthulhator.model.Relation;

import java.util.Collections;
import java.util.Set;

/**
 * Created by bhm on 26.09.14.
 */
public enum Proffession {
    Archeolog;

    public Set<Relation> getRelations() {
        return Collections.emptySet();
    }
}
