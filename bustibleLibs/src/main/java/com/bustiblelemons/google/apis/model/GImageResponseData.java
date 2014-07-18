package com.bustiblelemons.google.apis.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 18.07.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GImageResponseData {
    private List<GoogleImageObject> results = new ArrayList<GoogleImageObject>();
    private String moreResultsUrl;
    private String resultCount;

    public List<GoogleImageObject> getResults() {
        return results;
    }

    public String getMoreResultsUrl() {
        return moreResultsUrl;
    }

    public String getResultCount() {
        return resultCount;
    }
}
