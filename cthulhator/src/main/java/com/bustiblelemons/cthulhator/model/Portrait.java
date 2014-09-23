package com.bustiblelemons.cthulhator.model;

import java.io.Serializable;

/**
 * Created by bhm on 29.07.14.
 */
public class Portrait implements Serializable {
    private String  name;
    private String  url;
    private byte[]  data;
    private boolean isMain;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean isMain) {
        this.isMain = isMain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Portrait portrait = (Portrait) o;

        if (url != null ? !url.equals(portrait.url) : portrait.url != null) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }
}
