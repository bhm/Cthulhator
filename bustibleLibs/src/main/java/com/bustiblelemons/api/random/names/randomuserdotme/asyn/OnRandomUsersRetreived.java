package com.bustiblelemons.api.random.names.randomuserdotme.asyn;

import com.bustiblelemons.api.random.names.randomuserdotme.RandomUserMEQuery;
import com.bustiblelemons.api.random.names.randomuserdotme.model.User;

import java.util.List;

public interface OnRandomUsersRetreived {
    public int onRandomUsersRetreived(RandomUserMEQuery query, List<User> users);
}