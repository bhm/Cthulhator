package com.bustiblelemons.google.apis.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by bhm on 18.07.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleImageObject {
    private int width;
    private int height;
    private String unescapedUrl;
    private String url;
    private String title;
    private String tbUrl;
    private String visibleUrl;
    private String originalContextUrl;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setUnescapedUrl(String unescapedUrl) {
        this.unescapedUrl = unescapedUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTbUrl(String tbUrl) {
        this.tbUrl = tbUrl;
    }

    public void setVisibleUrl(String visibleUrl) {
        this.visibleUrl = visibleUrl;
    }

    public void setOriginalContextUrl(String originalContextUrl) {
        this.originalContextUrl = originalContextUrl;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getUnescapedUrl() {
        return unescapedUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getTbUrl() {
        return tbUrl;
    }

    public String getVisibleUrl() {
        return visibleUrl;
    }

    public String getOriginalContextUrl() {
        return originalContextUrl;
    }
}
