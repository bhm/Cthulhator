package com.bustiblelemons.api.random.names.randomuserdotme.asyn;

import android.content.Context;
import android.util.Pair;

import com.bustiblelemons.api.random.names.randomuserdotme.RandomUserMEQuery;
import com.bustiblelemons.api.random.names.randomuserdotme.model.RandomUserDotMe;
import com.bustiblelemons.api.random.names.randomuserdotme.model.Results;
import com.bustiblelemons.api.random.names.randomuserdotme.model.User;
import com.bustiblelemons.async.SimpleAsync;
import com.bustiblelemons.logging.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 25.07.14.
 */
public class RandomUserDotMeAsyn
        extends SimpleAsync<RandomUserMEQuery, Pair<RandomUserMEQuery, List<User>>> {

    private OnRandomUsersRetreived listener;
    private Logger                 log = new Logger(RandomUserDotMeAsyn.class);

    public RandomUserDotMeAsyn(Context context, OnRandomUsersRetreived listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected Pair<RandomUserMEQuery, List<User>> call(RandomUserMEQuery... queries) throws
                                                                                           Exception {
        Pair<RandomUserMEQuery, List<User>> r = Pair.create(null, null);
        if (queries != null) {
            for (RandomUserMEQuery query : queries) {
                log.d("Random query %s", query);
                List<User> users = getRandomUsers(query);
                Pair<RandomUserMEQuery, List<User>> pair = Pair.create(query, users);
                publishProgress(pair);
            }
        }
        return r;
    }

    private List<User> getRandomUsers(RandomUserMEQuery rudmQuery) throws IOException,
                                                                             URISyntaxException {
        List<User> r = new ArrayList<User>();
        if (rudmQuery != null) {
            RandomUserDotMe randomUser = rudmQuery.getObject(RandomUserDotMe.class);
            log.d("random user %s", randomUser.getResults().size());
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
    protected void onProgressUpdate(Pair<RandomUserMEQuery, List<User>>... values) {
        super.onProgressUpdate(values);
        if (values != null) {
            for (Pair<RandomUserMEQuery, List<User>> pair : values) {
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
    protected boolean onSuccess(Pair<RandomUserMEQuery, List<User>> result) {
        return false;
    }

}
