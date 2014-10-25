package com.bustiblelemons.randomuserdotme.logic;

import android.content.Context;

import com.bustiblelemons.async.AbsSimpleAsync;
import com.bustiblelemons.logging.Logger;
import com.bustiblelemons.randomuserdotme.model.RandomUserDotMe;
import com.bustiblelemons.randomuserdotme.model.RandomUserMe;
import com.bustiblelemons.randomuserdotme.model.Results;
import com.bustiblelemons.randomuserdotme.model.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bhm on 25.07.14.
 */
public class RandomUserDotMeAsyn extends AbsSimpleAsync<RandomUserMEQuery, List<User>> {

    private OnRandomUsersRetreived listener;
    private Logger       log      = new Logger(RandomUserDotMeAsyn.class);
    private RandomUserMe postPart = RandomUserMe.All;
    private OnFinishAllQueries onFinishAllQueries;

    public RandomUserDotMeAsyn(Context context, OnRandomUsersRetreived listener) {
        super(context);
        this.listener = listener;
    }

    public void setOnFinishAllQueries(OnFinishAllQueries onFinishAllQueries) {
        this.onFinishAllQueries = onFinishAllQueries;
    }

    @Override
    protected List<User> call(RandomUserMEQuery... queries) throws Exception {
        List<User> r = Collections.emptyList();
        int total = 0;
        if (queries != null) {
            for (RandomUserMEQuery query : queries) {
                total++;
                List<User> users = getRandomUsers(query);
                publishProgress(query, users);
            }
        }
        if (onFinishAllQueries != null) {
            onFinishAllQueries.onFinishAllQueries(this, total);
        }
        return r;
    }


    private List<User> getRandomUsers(RandomUserMEQuery rudmQuery) {
        List<User> r = new ArrayList<User>();
        if (rudmQuery != null) {
            RandomUserDotMe randomUser = null;
            try {
                randomUser = rudmQuery.getObject(RandomUserDotMe.class);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
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
    protected void onProgressUpdate(RandomUserMEQuery param, List<User> result) {
        postData(param, result);
    }

    private void postData(RandomUserMEQuery query, List<User> users) {
        switch (postPart) {
            default:
            case All:
                listener.onRandomUsersRetreived(query, users);
                break;
            case Names:
                listener.onRandomUsersNames(query, users);
                break;
            case Location:
                listener.onRandomUsersLocations(query, users);
                break;
        }
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    protected boolean onSuccess(List<User> result) {
        return false;
    }

    public void postPart(RandomUserMe postPart) {
        this.postPart = postPart;
    }

    public interface OnFinishAllQueries {
        void onFinishAllQueries(RandomUserDotMeAsyn async, int total);
    }
}
