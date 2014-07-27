
package com.bustiblelemons.api.random.names.randomuserdotme.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RandomUserDotMe implements Serializable {
    private List<Results> results;

    public List<Results> getResults() {
        return this.results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}
