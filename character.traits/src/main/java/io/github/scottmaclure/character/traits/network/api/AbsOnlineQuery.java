package io.github.scottmaclure.character.traits.network.api;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import io.github.scottmaclure.character.traits.logging.Logger;

/**
 * Created by bhm on 25.07.14.
 */
public abstract class AbsOnlineQuery implements OnlineQuery {

    private static final Logger log = new Logger(AbsOnlineQuery.class);

    protected abstract List<NameValuePair> getNameValuePairs();

    protected abstract boolean usesSSL();

    @Override
    public String getScheme() {
        return usesSSL() ? "https" : "http";
    }

    @Override
    public int getPort() {
        return -1;
    }

    protected URI getUri(List<NameValuePair> valuePairs) throws URISyntaxException {
        String query = URLEncodedUtils.format(valuePairs, "utf-8");
        return URIUtils.createURI(getScheme(), getHost(), getPort(), getMethod(), query,
                getFragment());
    }

    @Override
    public String getFragment() {
        return null;
    }

    @Override
    public HttpResponse query() throws IOException, URISyntaxException {
        List<NameValuePair> valuePairs = getNameValuePairs();
        URI uri = getUri(valuePairs);
        log.d("Uri %s", uri.toString());
        HttpGet get = new HttpGet(uri);
        HttpClient client = new DefaultHttpClient();
        return client.execute(get);
    }
}
