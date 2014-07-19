package com.bustiblelemons.google.apis.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 19.07.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GImageResponseData {
    private List<GoogleImageObject> results = new ArrayList<GoogleImageObject>();

    public List<GoogleImageObject> getResults() {
        return results;
    }

    public void setResults(List<GoogleImageObject> results) {
        this.results = results;
    }

}
