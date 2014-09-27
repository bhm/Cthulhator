package com.bustiblelemons.cthulhator.creation.characteristics.logic;

import android.content.Context;

import com.bustiblelemons.cthulhator.model.CharacterProperty;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import io.github.scottmaclure.character.traits.asyn.AbsAsynTask;

/**
 * Created by bhm on 27.09.14.
 */
public class CharacterPropertySortAsyn
        extends AbsAsynTask<Comparator<CharacterProperty>, Set<CharacterProperty>> {

    private final Set<CharacterProperty> toCompare;
    private       OnPropertiesSorted     onPropertiesSorted;

    public CharacterPropertySortAsyn(Context context, OnPropertiesSorted onPropertiesSorted,
                                     Set<CharacterProperty> toCompare) {
        super(context);
        this.onPropertiesSorted = onPropertiesSorted;
        this.toCompare = toCompare;
    }

    @Override
    protected Set<CharacterProperty> call(Comparator<CharacterProperty>... params) throws
                                                                                   Exception {
        Set<CharacterProperty> r = new TreeSet<CharacterProperty>();
        for (Comparator<CharacterProperty> comparator : params) {
            r = new TreeSet<CharacterProperty>(comparator);
            r.addAll(toCompare);
            publishProgress(comparator, r);
        }
        return r;
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    public void onProgressUpdate(Comparator<CharacterProperty> param, Set<CharacterProperty> result) {
        if (onPropertiesSorted != null) {
            onPropertiesSorted.onCharacterPropertiesSorted(param, result);
        }
    }

    public interface OnPropertiesSorted {
        void onCharacterPropertiesSorted(Comparator<CharacterProperty> comparator,
                                         Set<CharacterProperty> sortedSet);
    }
}
