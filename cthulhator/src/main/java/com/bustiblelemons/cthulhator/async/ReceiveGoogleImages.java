package com.bustiblelemons.cthulhator.async;

import com.bustiblelemons.google.apis.model.GoogleImageObject;
import com.bustiblelemons.google.apis.search.params.GImageSearch;

import java.util.List;

public interface ReceiveGoogleImages        {
    boolean onGoogleImageObjectsDownloaded(GImageSearch search, List<GoogleImageObject> objects);
}