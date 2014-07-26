package com.bustiblelemons.api.random.names.randomuserdotme.asyn;

import com.bustiblelemons.api.random.names.randomuserdotme.RandomUserDotMeQuery;
import com.bustiblelemons.api.random.names.randomuserdotme.model.User;

import java.util.List;

public interface OnRandomUsersRetreived {
    public int onRandomUsersRetreived(RandomUserDotMeQuery query, List<User> users);
}