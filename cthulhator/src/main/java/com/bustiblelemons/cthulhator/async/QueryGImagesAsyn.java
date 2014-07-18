package com.bustiblelemons.cthulhator.async;

import android.content.Context;

import com.bustiblelemons.async.SimpleAsync;
import com.bustiblelemons.google.apis.model.GImageResponseData;
import com.bustiblelemons.google.apis.model.GoogleImageObject;
import com.bustiblelemons.google.apis.search.params.ImageSearch;

import org.apache.http.HttpResponse;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by bhm on 18.07.14.
 */
public class QueryGImagesAsyn extends SimpleAsync<ImageSearch, List<GoogleImageObject>> {

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
    public List<GoogleImageObject> call(ImageSearch... params) throws Exception {
        for (ImageSearch search : params) {
            ObjectMapper mapper = new ObjectMapper();
            HttpResponse response = search.query();
            InputStream in = response.getEntity().getContent();
            try {
                GImageResponseData responseData = mapper.readValue(in, GImageResponseData.class);
                publishProgress(responseData.getResults());
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
    public boolean onException(Exception e) {
        return false;
    }

    @Override
    public boolean onSuccess(List<GoogleImageObject> result) {
        return false;
    }
}
