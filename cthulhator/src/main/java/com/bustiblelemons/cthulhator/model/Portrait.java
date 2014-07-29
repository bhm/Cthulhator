package com.bustiblelemons.cthulhator.model;

/**
 * Created by bhm on 29.07.14.
 */
public class Portrait {
    private String name;
    private String url;
    private byte[] data;
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
}
