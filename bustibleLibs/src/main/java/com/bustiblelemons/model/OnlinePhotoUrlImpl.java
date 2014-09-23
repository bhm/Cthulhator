package com.bustiblelemons.model;

/**
 * Created by bhm on 23.09.14.
 */
public class OnlinePhotoUrlImpl implements OnlinePhotoUrl {
    private String mUrl;

    public OnlinePhotoUrlImpl(String url) {
        this.mUrl = url;
    }

    @Override
    public String getUrl() {
        return mUrl;
    }
}
