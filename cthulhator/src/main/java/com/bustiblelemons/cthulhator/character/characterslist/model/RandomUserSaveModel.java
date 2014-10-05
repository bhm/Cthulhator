package com.bustiblelemons.cthulhator.character.characterslist.model;

import com.bustiblelemons.randomuserdotme.model.User;

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