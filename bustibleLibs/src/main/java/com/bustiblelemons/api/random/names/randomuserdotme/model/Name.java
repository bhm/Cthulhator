
package com.bustiblelemons.api.random.names.randomuserdotme.model;

import org.apache.commons.lang.WordUtils;

import java.io.Serializable;

public class Name implements Serializable {
    private String first;
    private String last;
    private String title;

    public String getFirst() {
        return WordUtils.capitalizeFully(this.first);
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return WordUtils.capitalizeFully(this.last);
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getTitle() {
        return WordUtils.capitalizeFully(this.title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullName() {
        return String.format("%s %s", getFirst(), getLast());
    }
}
