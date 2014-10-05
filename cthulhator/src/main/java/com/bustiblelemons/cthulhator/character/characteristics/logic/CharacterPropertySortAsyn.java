package com.bustiblelemons.cthulhator.character.characteristics.logic;

import android.content.Context;
import android.util.Pair;

import com.bustiblelemons.async.AbsExtendedAsync;
import com.bustiblelemons.cthulhator.system.properties.ActionGroup;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Created by bhm on 27.09.14.
 */
public class CharacterPropertySortAsyn
        extends AbsExtendedAsync<Comparator<CharacterProperty>, ActionGroup, List<CharacterProperty>> {

    private final Set<CharacterProperty> toCompare;
    private       OnPropertiesSorted     onPropertiesSorted;

    public CharacterPropertySortAsyn(Context context, OnPropertiesSorted onPropertiesSorted,
                                     Set<CharacterProperty> toCompare) {
        super(context);
        this.onPropertiesSorted = onPropertiesSorted;
        this.toCompare = toCompare;
    }

    @Override
    protected Pair<ActionGroup, List<CharacterProperty>> call(Comparator<CharacterProperty>... params)
            throws Exception {
        for (Comparator<CharacterProperty> comparator : params) {
            List<CharacterProperty> post = new ArrayList<CharacterProperty>();
            post.addAll(toCompare);
            Collections.sort(post, comparator);
            publishProgress(comparator, null, post);
        }
        return Pair.create(null, Collections.<CharacterProperty>emptyList());
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    public void onProgressUpdate(Comparator<CharacterProperty> param,
                                 ActionGroup ret1,
                                 List<CharacterProperty> ret2) {
        if (onPropertiesSorted != null) {
            if (ret1 != null) {
                onPropertiesSorted.onCharacterPropertiesSortedByGroup(param, ret1, ret2);
            } else {
                onPropertiesSorted.onCharacterPropertiesSorted(param, ret2);
            }
        }

    }


    public interface OnPropertiesSorted {
        void onCharacterPropertiesSorted(Comparator<CharacterProperty> comparator,
                                         List<CharacterProperty> sortedSet);

        void onCharacterPropertiesSortedByGroup(Comparator<CharacterProperty> comparator,
                                                ActionGroup group,
                                                List<CharacterProperty> sortedSet);
    }

}
