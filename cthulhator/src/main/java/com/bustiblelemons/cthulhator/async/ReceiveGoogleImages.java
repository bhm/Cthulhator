package com.bustiblelemons.cthulhator.async;

import com.bustiblelemons.google.apis.search.params.GImageSearch;
import com.bustiblelemons.model.OnlinePhotoUrl;

import java.util.List;

public interface ReceiveGoogleImages {
    boolean onGoogleImageObjectsDownloaded(GImageSearch search, List<OnlinePhotoUrl> objects);
}