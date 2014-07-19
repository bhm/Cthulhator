package com.bustiblelemons.google.apis.search.params;

import com.bustiblelemons.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by bhm on 18.07.14.
 */
public class GoogleImageSearch implements ImageSearch {


    private              List<Enum<?>>      params         = new ArrayList<Enum<?>>();
    private              int                resultsPerPage = ImageSearch.rsz;
    private              Collection<String> sites          = new ArrayList<String>();
    private              int                start          = 0 - resultsPerPage;
    private              String             mQuery         = "";
    private static final Logger             log            = new Logger(GoogleImageSearch.class);

    private GoogleImageSearch(Options b) {
        params.add(b.filetype);
        params.add(b.safe);
        params.add(b.rights);
        params.add(b.imagesize);
        params.add(b.rights);
        params.add(b.imagetype);
        params.add(b.imagecolor);
        resultsPerPage = b.resultsPerPage;
        sites = b.sites;
        start = b.start;
        mQuery = b.query;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    private Enum<?> getValue(Class<? extends Enum> e) {
        for (Enum __e : params) {
            if (__e.getClass().equals(e.getClass())) {
                return __e;
            }
        }
        return null;
    }

    @Override
    public safe getSafeMode() {
        return (safe) getValue(safe.class);
    }

    @Override
    public imgtype getImageType() {
        return (imgtype) getValue(imgtype.class);
    }

    @Override
    public as_filetype getFileType() {
        return (as_filetype) getValue(as_filetype.class);
    }

    @Override
    public imgsz getImageSize() {
        return (imgsz) getValue(imgsz.class);
    }

    @Override
    public as_rights getRights() {
        return (as_rights) getValue(as_rights.class);
    }

    @Override
    public HttpResponse query(String query) throws IOException, URISyntaxException {
        this.mQuery = query;
        this.start = 0 - resultsPerPage;
        return query();
    }

    @Override
    public HttpResponse query() throws IOException, URISyntaxException {
        List<NameValuePair> valuePairs = getNameValuePairs();
        URI uri = getUri(valuePairs);
        HttpGet get = new HttpGet(uri);
        HttpClient client = new DefaultHttpClient();
        return client.execute(get);
    }

    private List<NameValuePair> getNameValuePairs() {
        List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
        valuePairs.add(new BasicNameValuePair(VERSION, VERSION_1));
        fillEnumParams(valuePairs);
        fillResultsPerPage(valuePairs);
        fillQuery(this.mQuery, valuePairs);
        return valuePairs;
    }

    private URI getUri(List<NameValuePair> valuePairs) throws URISyntaxException {
        String query = URLEncodedUtils.format(valuePairs, "utf-8");
        return URIUtils.createURI(SCHEME, HOST, -1, METHOD, query, null);
    }

    private void fillQuery(String mQuery, List<NameValuePair> valuePairs) {
        valuePairs.add(new BasicNameValuePair(QUERY, mQuery));
    }

    private void fillResultsPerPage(List<NameValuePair> valuePairs) {
        valuePairs.add(new BasicNameValuePair(RESULTS_PER_PAGE, resultsPerPage + ""));
        this.start += this.resultsPerPage;
        log.d("Start %s", start);
        valuePairs.add(new BasicNameValuePair(START, start + ""));
    }

    private void fillEnumParams(List<NameValuePair> valuePairs) {
        for (Enum<?> e : params) {
            String name = e.getClass().getSimpleName();
            String value = e.name();
            valuePairs.add(new BasicNameValuePair(name, value));
        }
    }


    public static class Options {
        protected safe        safe       = ImageSearch.safe.off;
        protected as_filetype filetype   = ImageSearch.as_filetype.png;
        protected as_rights   rights     = ImageSearch.as_rights.none;
        protected imgtype     imagetype  = imgtype.face;
        protected imgsz       imagesize  = imgsz.large;
        protected imgc        imagecolor = imgc.color;

        protected int resultsPerPage = ImageSearch.rsz;

        public Collection<String> sites = new ArrayList<String>();
        public int                start = 0;

        protected String query = "";


        public void start(int start) {
            this.start = start;
        }

        public Options safe(ImageSearch.safe safe) {
            this.safe = safe;
            return this;
        }

        public Options setFiletype(as_filetype filetype) {
            this.filetype = filetype;
            return this;
        }

        public Options setRights(as_rights rights) {
            this.rights = rights;
            return this;
        }

        public Options setImageType(imgtype imagetype) {
            this.imagetype = imagetype;
            return this;
        }

        public Options setImageSize(imgsz imagesize) {
            this.imagesize = imagesize;
            return this;
        }

        public Options setImageColor(imgc imagecolor) {
            this.imagecolor = imagecolor;
            return this;
        }

        public Options setResultsPerPage(int resultsPerPage) {
            this.resultsPerPage = resultsPerPage;
            return this;
        }

        public Options setSites(Collection<String> sites) {
            this.sites = sites;
            return this;
        }

        public ImageSearch build() {
            return new GoogleImageSearch(this);
        }

        public void nextPage() {
            start += resultsPerPage;
        }

        public void setQuery(String query) {
            this.query = query;
        }
    }
}
