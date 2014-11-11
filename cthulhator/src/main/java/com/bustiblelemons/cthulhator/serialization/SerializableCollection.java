package com.bustiblelemons.cthulhator.serialization;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by hiv on 11.11.14.
 */
public abstract class SerializableCollection<T extends Serializable> implements Serializable {

    private Collection<T> mData;

    public Collection<T> getData() {
        return mData;
    }

    public void setData(Collection<T> data) {
        this.mData = data;
    }
}
