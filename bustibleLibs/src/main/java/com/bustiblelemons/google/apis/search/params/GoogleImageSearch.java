package com.bustiblelemons.google.apis.search.params;

import com.bustiblelemons.api.AbsOnlineQuery;
import com.bustiblelemons.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by bhm on 18.07.14.
 */
public class GoogleImageSearch extends AbsOnlineQuery implements GImageSearch {


    private              List<Enum<?>>      params         = new ArrayList<Enum<?>>();
    private              int                resultsPerPage = GImageSearch.rsz;
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
    public GImageSearch.safe getSafeMode() {
        return (GImageSearch.safe) getValue(GImageSearch.safe.class);
    }

    @Override
    public GImageSearch.imgtype getImageType() {
        return (GImageSearch.imgtype) getValue(GImageSearch.imgtype.class);
    }

    @Override
    public GImageSearch.as_filetype getFileType() {
        return (GImageSearch.as_filetype) getValue(GImageSearch.as_filetype.class);
    }

    @Override
    public GImageSearch.imgsz getImageSize() {
        return (GImageSearch.imgsz) getValue(GImageSearch.imgsz.class);
    }

    @Override
    public GImageSearch.as_rights getRights() {
        return (GImageSearch.as_rights) getValue(GImageSearch.as_rights.class);
    }

    @Override
    public HttpResponse query(String query) throws IOException, URISyntaxException {
        this.mQuery = query;
        this.start = 0 - resultsPerPage;
        return query();
    }

    @Override
    protected List<NameValuePair> getNameValuePairs() {
        List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
        valuePairs.add(new BasicNameValuePair(GImageSearch.VERSION, GImageSearch.VERSION_1));
        fillEnumParams(valuePairs);
        fillResultsPerPage(valuePairs);
        fillQuery(this.mQuery, valuePairs);
        return valuePairs;
    }

    @Override
    protected boolean usesSSL() {
        return true;
    }

    private void fillQuery(String mQuery, List<NameValuePair> valuePairs) {
        valuePairs.add(new BasicNameValuePair(QUERY, mQuery));
    }

    private void fillResultsPerPage(List<NameValuePair> valuePairs) {
        valuePairs.add(new BasicNameValuePair(RESULTS_PER_PAGE, resultsPerPage + ""));
        this.start += this.resultsPerPage;
//        log.d("Start %s", start);
        valuePairs.add(new BasicNameValuePair(START, start + ""));
    }

    private void fillEnumParams(List<NameValuePair> valuePairs) {
        for (Enum<?> e : params) {
            if (e != null) {
                String name = e.getClass().getSimpleName();
                String value = e.name();
                valuePairs.add(new BasicNameValuePair(name, value));
            }
        }
    }

    @Override
    public String getScheme() {
        return GImageSearch.SCHEME;
    }

    @Override
    public String getHost() {
        return GImageSearch.HOST;
    }

    @Override
    public String getMethod() {
        return GImageSearch.METHOD;
    }


    public static class Options implements Serializable {
        protected GImageSearch.safe        safe       = GImageSearch.safe.off;
        protected GImageSearch.as_filetype filetype   = null;
        protected GImageSearch.as_rights   rights     = null;
        protected GImageSearch.imgtype     imagetype  = GImageSearch.imgtype.face;
        protected GImageSearch.imgsz                    imagesize  = null;
        protected GImageSearch.imgc                     imagecolor = null;

        protected int resultsPerPage = GImageSearch.rsz;

        public Collection<String> sites = new ArrayList<String>();
        public int                start = 0;

        protected String query = "";

        public void start(int start) {
            this.start = start;
        }

        public Options safe(GImageSearch.safe safe) {
            this.safe = safe;
            return this;
        }

        public Options setFiletype(GImageSearch.as_filetype filetype) {
            this.filetype = filetype;
            return this;
        }

        public Options setRights(GImageSearch.as_rights rights) {
            this.rights = rights;
            return this;
        }

        public Options setImageType(GImageSearch.imgtype imagetype) {
            this.imagetype = imagetype;
            return this;
        }

        public Options setImageSize(GImageSearch.imgsz imagesize) {
            this.imagesize = imagesize;
            return this;
        }

        public Options setImageColor(GImageSearch.imgc imagecolor) {
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

        public GImageSearch build() {
            return new GoogleImageSearch(this);
        }

        public void nextPage() {
            start += resultsPerPage;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getQuery() {
            return this.query;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        GoogleImageSearch that = (GoogleImageSearch) o;

        if (resultsPerPage != that.resultsPerPage) { return false; }
        if (start != that.start) { return false; }
        if (mQuery != null ? !mQuery.equals(that.mQuery) : that.mQuery != null) { return false; }
        if (params != null ? !params.equals(that.params) : that.params != null) { return false; }
        if (sites != null ? !sites.equals(that.sites) : that.sites != null) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        int result = params != null ? params.hashCode() : 0;
        result = 31 * result + resultsPerPage;
        result = 31 * result + (sites != null ? sites.hashCode() : 0);
        result = 31 * result + start;
        result = 31 * result + (mQuery != null ? mQuery.hashCode() : 0);
        return result;
    }
}
