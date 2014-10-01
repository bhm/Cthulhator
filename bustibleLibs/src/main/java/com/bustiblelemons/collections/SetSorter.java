package com.bustiblelemons.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by bhm on 01.10.14.
 */
public abstract class SetSorter<E> {
    private Comparator<E> comparator;

    private Collection<E> mCollectionToSort;

    public <T extends SetSorter<E>> T collection(Collection<E> collection) {
        if (collection == null) {
            mCollectionToSort = new ArrayList<E>();
            return (T) this;
        }
        mCollectionToSort = collection;
        return (T) this;
    }

    public Set<E> sort() {
        Set<E> r = new TreeSet<E>(comparator == null ? getDefaultComparator() : comparator);
        r.addAll(mCollectionToSort);
        return r;
    }

    public <T extends SetSorter<E>> T setComparator(Comparator<E> comparator) {
        this.comparator = comparator;
        return (T) this;
    }

    public abstract Comparator<E> getDefaultComparator();
}
