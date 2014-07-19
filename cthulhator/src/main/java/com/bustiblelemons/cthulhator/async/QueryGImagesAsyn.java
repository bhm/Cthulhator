package com.bustiblelemons.cthulhator.async;

import android.content.Context;

import com.bustiblelemons.async.SimpleAsync;
import com.bustiblelemons.google.apis.model.GoogleImageObject;
import com.bustiblelemons.google.apis.model.GoogleImageResponse;
import com.bustiblelemons.google.apis.search.params.ImageSearch;
import com.bustiblelemons.logging.Logger;

import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by bhm on 18.07.14.
 */
public class QueryGImagesAsyn extends SimpleAsync<ImageSearch, List<GoogleImageObject>> {

    private Logger log = new Logger(QueryGImagesAsyn.class);

    public QueryGImagesAsyn(Context context) {
        super(context);
    }

    public interface ReceiveGoogleImages {
        public boolean onGoogleImagesReceived(List<GoogleImageObject> results);
    }

    private ReceiveGoogleImages listener;

    public QueryGImagesAsyn(Context context, ReceiveGoogleImages listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected List<GoogleImageObject> call(ImageSearch... params) throws Exception {
        for (ImageSearch search : params) {
            ObjectMapper mapper = new ObjectMapper();
            HttpResponse httpResponse = search.query();
            InputStream in = httpResponse.getEntity().getContent();
            try {
                GoogleImageResponse response = mapper.readValue(in, GoogleImageResponse.class);
                log.d("Response \n%s", response.getResults().size());
                publishProgress(response.getResults());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(List<GoogleImageObject>... values) {
        super.onProgressUpdate(values);
        if (values != null && listener != null) {
            for (List<GoogleImageObject> result : values) {
                listener.onGoogleImagesReceived(result);
            }
        }
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    protected boolean onSuccess(List<GoogleImageObject> result) {
        return false;
    }
}
