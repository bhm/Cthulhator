package com.bustiblelemons.cthulhator.async;

import android.content.Context;
import android.util.Pair;

import com.bustiblelemons.async.SimpleAsync;
import com.bustiblelemons.google.apis.model.GoogleImageObject;
import com.bustiblelemons.google.apis.model.GoogleImageResponse;
import com.bustiblelemons.google.apis.search.params.GImageSearch;
import com.bustiblelemons.logging.Logger;

import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by bhm on 18.07.14.
 */
public class QueryGImagesAsyn extends SimpleAsync<GImageSearch, Pair<GImageSearch, List<GoogleImageObject>>> {

    private Logger log = new Logger(QueryGImagesAsyn.class);

    public QueryGImagesAsyn(Context context) {
        super(context);
    }

    public interface ReceiveGoogleImages {
        public boolean onGoogleImagesReceived(GImageSearch first, List<GoogleImageObject> results);
    }

    private ReceiveGoogleImages listener;

    public QueryGImagesAsyn(Context context, ReceiveGoogleImages listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected Pair<GImageSearch, List<GoogleImageObject>> call(GImageSearch... params) throws Exception {
        for (GImageSearch search : params) {
            ObjectMapper mapper = new ObjectMapper();
            HttpResponse httpResponse = search.query();
            InputStream in = httpResponse.getEntity().getContent();
            try {
                GoogleImageResponse response = mapper.readValue(in, GoogleImageResponse.class);
                log.d("Response \n%s", response.getResults().size());
                publishProgress(search, response.getResults());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void publishProgress(GImageSearch search, List<GoogleImageObject> results) {
        Pair<GImageSearch, List<GoogleImageObject>> progress;
        progress = Pair.create(search, results);
    }

    @Override
    protected void onProgressUpdate(Pair<GImageSearch, List<GoogleImageObject>>... values) {
        super.onProgressUpdate(values);
        if (values != null && listener != null) {
            for (Pair<GImageSearch, List<GoogleImageObject>> pair : values) {
                listener.onGoogleImagesReceived(pair.first, pair.second);
            }
        }
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    protected boolean onSuccess(Pair<GImageSearch, List<GoogleImageObject>> result) {
        return false;
    }
}
