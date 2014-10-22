package io.github.scottmaclure.character.traits.network.api;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * Created by bhm on 25.07.14.
 */
public abstract class AbsJacksonQuery<T> extends AbsOnlineQuery {

    public T getObject(Class<T> clss) throws IOException, URISyntaxException {
        HttpResponse response = query();
        HttpEntity httpEntity = response.getEntity();
        Log.d(getClass().getSimpleName(), "http Entity " + httpEntity);
        if (httpEntity != null) {
            InputStream in = response.getEntity().getContent();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(in, clss);
        }
        return null;
    }
}
