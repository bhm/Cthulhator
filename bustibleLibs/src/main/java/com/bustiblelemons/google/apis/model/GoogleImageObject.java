package com.bustiblelemons.google.apis.model;

import com.bustiblelemons.model.OnlinePhotoUrl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Created by bhm on 18.07.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleImageObject implements OnlinePhotoUrl {
    private int width;
    private int height;
    private String unescapedUrl;
    private String url;
    private String title;
    private String tbUrl;
    private String visibleUrl;
    private String originalContextUrl;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUnescapedUrl() {
        return unescapedUrl;
    }

    public void setUnescapedUrl(String unescapedUrl) {
        this.unescapedUrl = unescapedUrl;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTbUrl() {
        return tbUrl;
    }

    public void setTbUrl(String tbUrl) {
        this.tbUrl = tbUrl;
    }

    public String getVisibleUrl() {
        return visibleUrl;
    }

    public void setVisibleUrl(String visibleUrl) {
        this.visibleUrl = visibleUrl;
    }

    public String getOriginalContextUrl() {
        return originalContextUrl;
    }

    public void setOriginalContextUrl(String originalContextUrl) {
        this.originalContextUrl = originalContextUrl;
    }
}
