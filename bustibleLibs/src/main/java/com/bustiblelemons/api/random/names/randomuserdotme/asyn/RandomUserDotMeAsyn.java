package com.bustiblelemons.api.random.names.randomuserdotme.asyn;

import android.content.Context;
import android.util.Pair;

import com.bustiblelemons.api.random.names.randomuserdotme.RandomUserDotMeQuery;
import com.bustiblelemons.api.random.names.randomuserdotme.model.RandomUserDotMe;
import com.bustiblelemons.api.random.names.randomuserdotme.model.Results;
import com.bustiblelemons.api.random.names.randomuserdotme.model.User;
import com.bustiblelemons.async.SimpleAsync;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 25.07.14.
 */
public class RandomUserDotMeAsyn
        extends SimpleAsync<RandomUserDotMeQuery, Pair<RandomUserDotMeQuery, List<User>>> {

    private OnRandomUsersRetreived listener;

    public RandomUserDotMeAsyn(Context context, OnRandomUsersRetreived listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected Pair<RandomUserDotMeQuery, List<User>> call(RandomUserDotMeQuery... queries) throws
                                                                                           Exception {
        Pair<RandomUserDotMeQuery, List<User>> r = Pair.create(null, null);
        if (queries != null) {
            for (RandomUserDotMeQuery query : queries) {
                List<User> users = getRandomUsers(query);
                Pair<RandomUserDotMeQuery, List<User>> pair = Pair.create(query, users);
                publishProgress(pair);
            }
        }
        return r;
    }

    private List<User> getRandomUsers(RandomUserDotMeQuery rudmQuery) throws IOException,
                                                                             URISyntaxException {
        List<User> r = new ArrayList<User>();
        if (rudmQuery != null) {
            RandomUserDotMe randomUser = rudmQuery.getObject(RandomUserDotMe.class);
            for (Results result : randomUser.getResults()) {
                User u = result.getUser();
                if (u != null) {
                    r.add(u);
                }
            }
        }
        return r;
    }

    @Override
    protected void onProgressUpdate(Pair<RandomUserDotMeQuery, List<User>>... values) {
        super.onProgressUpdate(values);
        if (values != null) {
            for (Pair<RandomUserDotMeQuery, List<User>> pair : values) {
                if (listener != null) {
                    listener.onRandomUsersRetreived(pair.first, pair.second);
                }
            }
        }
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    protected boolean onSuccess(Pair<RandomUserDotMeQuery, List<User>> result) {
        return false;
    }

}
