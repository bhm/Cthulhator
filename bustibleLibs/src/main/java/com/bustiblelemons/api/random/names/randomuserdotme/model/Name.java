
package com.bustiblelemons.api.random.names.randomuserdotme.model;

import java.io.Serializable;

public class Name implements Serializable {
    private String first;
    private String last;
    private String title;

    public String getFirst() {
        return this.first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return this.last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullName() {
        return String.format("%s %s", getFirst(), getLast());
    }
}
