package com.bustiblelemons.randomuserdotme.logic;

import com.bustiblelemons.model.OnlinePhotoUrl;
import com.bustiblelemons.randomuserdotme.model.User;

import java.util.List;

public interface OnRandomUsersRetreived {
    public int onRandomUsersRetreived(RandomUserMEQuery query, List<User> users);

    public int onRandomUsersLocations(RandomUserMEQuery query, List<User> users);

    public int onRandomUsersNames(RandomUserMEQuery query, List<User> users);

    public int onRandomUsersPortraits(RandomUserMEQuery query, List<OnlinePhotoUrl> users);

}