package com.bustiblelemons.cthulhator.cache;

import com.bustiblelemons.api.random.names.randomuserdotme.model.User;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RandomUserSaveModel {

    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}