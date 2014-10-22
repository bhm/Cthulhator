package com.bustiblelemons.google.apis.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collections;
import java.util.List;

/**
 * Created by bhm on 18.07.14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleImageResponse {
    private GImageResponseData responseData;
    private String responseDetails;
    private int responseStatus;

    public List<GoogleImageObject> getResults() {
        return responseData != null ? responseData.getResults() :
                Collections.<GoogleImageObject>emptyList();
    }

    public GImageResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(GImageResponseData responseData) {
        this.responseData = responseData;
    }

    public String getResponseDetails() {
        return responseDetails;
    }

    public void setResponseDetails(String responseDetails) {
        this.responseDetails = responseDetails;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }
}