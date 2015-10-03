package com.bustiblelemons.cthulhator.character.characteristics.logic;

import android.content.Context;
import android.util.Pair;

import com.bustiblelemons.async.AbsExtendedAsync;
import com.bustiblelemons.cthulhator.system.properties.ActionGroup;
import com.bustiblelemons.cthulhator.system.properties.CharacterProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bhm on 27.09.14.
 */
public class CharacterPropertySortAsyn
        extends
        AbsExtendedAsync<Comparator<CharacterProperty>, ActionGroup, List<CharacterProperty>> {

    private final Set<CharacterProperty> toCompare;
    private       OnPropertiesSorted     onPropertiesSorted;

    private ActionGroup mActionGroupToFilter;

    public CharacterPropertySortAsyn(Context context, OnPropertiesSorted onPropertiesSorted,
                                     Collection<CharacterProperty> toCompare) {
        super(context);
        this.onPropertiesSorted = onPropertiesSorted;
        this.toCompare = new HashSet<CharacterProperty>(toCompare);
    }

    public CharacterPropertySortAsyn withActionGroupFilter(ActionGroup actionGroupFilter) {
        mActionGroupToFilter = actionGroupFilter;
        return this;
    }

    @Override
    protected Pair<ActionGroup, List<CharacterProperty>> call(Comparator<CharacterProperty>... params)
            throws Exception {
        for (Comparator<CharacterProperty> comparator : params) {
            List<CharacterProperty> list = new ArrayList<CharacterProperty>();
            if (mActionGroupToFilter != null) {
                publishFiltered(comparator, list);
            } else {
                publishUnfiltered(comparator);
            }
        }
        return Pair.create(mActionGroupToFilter, Collections.<CharacterProperty>emptyList());
    }

    private void publishUnfiltered(Comparator<CharacterProperty> comparator) {
        List<CharacterProperty> compared = new ArrayList<CharacterProperty>();
        compared.addAll(toCompare);
        Collections.sort(compared, comparator);
        publishProgress(comparator, null, compared);
    }

    private void publishFiltered(Comparator<CharacterProperty> comparator, List<CharacterProperty> list) {
        for (CharacterProperty property : toCompare) {
            if (property != null
                    && property.getActionGroup().equals(mActionGroupToFilter)) {
                list.add(property);
            }
        }
        Collections.sort(list, comparator);
        publishProgress(comparator, null, list);
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
