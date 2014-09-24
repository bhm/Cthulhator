package com.bustiblelemons.api.random.names.randomuserdotme.asyn;

import com.bustiblelemons.api.random.names.randomuserdotme.RandomUserMEQuery;
import com.bustiblelemons.api.random.names.randomuserdotme.model.User;
import com.bustiblelemons.model.OnlinePhotoUrl;

import java.util.List;

public interface OnRandomUsersRetreived {
    public int onRandomUsersRetreived(RandomUserMEQuery query, List<User> users);

    public int onRandomUsersLocations(RandomUserMEQuery query, List<User> users);

    public int onRandomUsersNames(RandomUserMEQuery query, List<User> users);

    public int onRandomUsersPortraits(RandomUserMEQuery query, List<OnlinePhotoUrl> users);

}