package com.bustiblelemons.api.random.names.randomuserdotme.asyn;

import android.content.Context;
import android.util.Pair;

import com.bustiblelemons.api.random.names.randomuserdotme.RandomUserMEQuery;
import com.bustiblelemons.api.random.names.randomuserdotme.model.RandomUserDotMe;
import com.bustiblelemons.api.random.names.randomuserdotme.model.Results;
import com.bustiblelemons.api.random.names.randomuserdotme.model.User;
import com.bustiblelemons.async.AbsSimpleAsync;
import com.bustiblelemons.logging.Logger;
import com.bustiblelemons.model.OnlinePhotoUrl;
import com.bustiblelemons.model.OnlinePhotoUrlImpl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bhm on 24.09.14.
 */
public class RandomUserDotMePortraitsAsyn extends
                                          AbsSimpleAsync<RandomUserMEQuery, List<OnlinePhotoUrl>> {
    private OnRandomUsersRetreived listener;
    private Logger log = new Logger(RandomUserDotMeAsyn.class);

    public RandomUserDotMePortraitsAsyn(Context context, OnRandomUsersRetreived listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected List<OnlinePhotoUrl> call(RandomUserMEQuery... queries) throws Exception {
        List<OnlinePhotoUrl> r = Collections.emptyList();
        if (queries != null) {
            for (RandomUserMEQuery query : queries) {
                List<OnlinePhotoUrl> photos = getRandomUserPictures(query);
                publishProgress(query, photos);
            }
        }
        return r;
    }


    private List<OnlinePhotoUrl> getRandomUserPictures(RandomUserMEQuery rudmQuery) {
        List<OnlinePhotoUrl> r = new ArrayList<OnlinePhotoUrl>();
        if (rudmQuery != null) {
            RandomUserDotMe randomUser = null;
            try {
                randomUser = rudmQuery.getObject(RandomUserDotMe.class);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            log.d("random user %s", randomUser);
            for (Results result : randomUser.getResults()) {
                User u = result.getUser();
                if (u != null && u.getPicture() != null) {
                    OnlinePhotoUrl o = new OnlinePhotoUrlImpl(u.getPicture().getMedium());
                    r.add(o);
                }
            }
        }
        return r;
    }

    @Override
    protected void onProgressUpdate(Pair<RandomUserMEQuery, List<OnlinePhotoUrl>>... values) {
        super.onProgressUpdate(values);
        if (values != null) {
            for (Pair<RandomUserMEQuery, List<OnlinePhotoUrl>> pair : values) {
                postData(pair);
            }
        }
    }

    private void postData(Pair<RandomUserMEQuery, List<OnlinePhotoUrl>> pair) {
        if (listener != null) {
            listener.onRandomUsersPortraits(pair.first, pair.second);
        }
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    protected boolean onSuccess(List<OnlinePhotoUrl> result) {
        return false;
    }

}
