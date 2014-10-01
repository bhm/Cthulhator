package com.bustiblelemons.cthulhator.creation.characteristics.logic;

import android.content.Context;
import android.util.Pair;

import com.bustiblelemons.async.AbsExtendedAsync;
import com.bustiblelemons.cthulhator.model.ActionGroup;
import com.bustiblelemons.cthulhator.model.CharacterProperty;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by bhm on 27.09.14.
 */
public class CharacterPropertySortAsyn
        extends AbsExtendedAsync<Comparator<CharacterProperty>, ActionGroup, Set<CharacterProperty>> {

    private final Set<CharacterProperty> toCompare;
    private       OnPropertiesSorted     onPropertiesSorted;
    private boolean mGroupBy = true;

    public CharacterPropertySortAsyn(Context context, OnPropertiesSorted onPropertiesSorted,
                                     Set<CharacterProperty> toCompare) {
        super(context);
        this.onPropertiesSorted = onPropertiesSorted;
        this.toCompare = toCompare;
    }

    @Override
    protected Pair<ActionGroup, Set<CharacterProperty>> call(Comparator<CharacterProperty>... params)
            throws Exception {
        for (Comparator<CharacterProperty> comparator : params) {
            if (!mGroupBy) {
                Set<CharacterProperty> r = new TreeSet<CharacterProperty>(comparator);
                r.addAll(toCompare);
                publishProgress(comparator, null, r);
            } else {
                for (ActionGroup group : ActionGroup.valuesWithoutOther()) {
                    Set<CharacterProperty> sorted = new TreeSet<CharacterProperty>(comparator);
                    for (CharacterProperty property : toCompare) {
                        if (property != null) {
                            List<ActionGroup> groups = property.getActionGroup();
                            if (groups != null && groups.contains(group)) {
                                sorted.add(property);
                            }
                        }
                    }
                    publishProgress(comparator, group, sorted);
                }
                Set<CharacterProperty> r = new TreeSet<CharacterProperty>(comparator);
                r.addAll(toCompare);
                publishProgress(comparator, ActionGroup.OTHER, r);
            }
        }
        return Pair.create(null, Collections.<CharacterProperty>emptySet());
    }
//
//    private ActionGroup forGroup(ActionGroup group) {
//        ActionGroup r = new SkillGroupHeader();
//        if (group != null) {
//            r.setGroup(group);
//            String clssName = ActionGroup.class.getSimpleName().toLowerCase(Locale.ENGLISH);
//            String name = group.name().toLowerCase(Locale.ENGLISH);
//            String s = ResourceHelper.from(getContext())
//                    .withNameParts(clssName, name)
//                    .getString();
//            r.setTitle(s);
//        }
//        return r;
//    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    public void onProgressUpdate(Comparator<CharacterProperty> param,
                                 ActionGroup ret1,
                                 Set<CharacterProperty> ret2) {
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
                                         Set<CharacterProperty> sortedSet);

        void onCharacterPropertiesSortedByGroup(Comparator<CharacterProperty> comparator,
                                                ActionGroup group,
                                                Set<CharacterProperty> sortedSet);
    }

}
