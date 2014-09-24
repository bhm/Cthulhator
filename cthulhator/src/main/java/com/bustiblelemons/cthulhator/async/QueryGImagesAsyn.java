package com.bustiblelemons.cthulhator.async;

import android.content.Context;

import com.bustiblelemons.google.apis.model.GoogleImageResponse;
import com.bustiblelemons.google.apis.search.params.GImageSearch;
import com.bustiblelemons.logging.Logger;
import com.bustiblelemons.model.OnlinePhotoUrl;

import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.scottmaclure.character.traits.asyn.AbsAsynTask;

/**
 * Created by bhm on 18.07.14.
 */
public class QueryGImagesAsyn extends AbsAsynTask<GImageSearch, List<OnlinePhotoUrl>> {

    private final ReceiveGoogleImages listener;
    private Logger log = new Logger(QueryGImagesAsyn.class);

    public QueryGImagesAsyn(Context context, ReceiveGoogleImages listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected List<OnlinePhotoUrl> call(GImageSearch... params) throws Exception {
        for (GImageSearch search : params) {
            ObjectMapper mapper = new ObjectMapper();
            HttpResponse httpResponse = search.query();
            InputStream in = httpResponse.getEntity().getContent();
            try {
                GoogleImageResponse response = mapper.readValue(in, GoogleImageResponse.class);
                log.d("Response \n%s", response.getResults().size());
                List<OnlinePhotoUrl> photoUrls = new ArrayList<OnlinePhotoUrl>();
                Collections.copy(photoUrls, response.getResults());
                publishProgress(search, photoUrls);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    public void onProgressUpdate(GImageSearch param, List<OnlinePhotoUrl> result) {
        if (listener != null) {
            listener.onGoogleImageObjectsDownloaded(param, result);
        }
    }

}
