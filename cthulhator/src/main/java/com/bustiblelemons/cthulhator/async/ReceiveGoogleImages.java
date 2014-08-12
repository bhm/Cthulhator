package com.bustiblelemons.cthulhator.async;

import com.bustiblelemons.google.apis.model.GoogleImageObject;
import com.bustiblelemons.google.apis.search.params.GImageSearch;

import java.util.List;

import io.github.scottmaclure.character.traits.network.api.asyn.AbsAsynTask;

public interface ReceiveGoogleImages
        extends AbsAsynTask.AsynCallback<GImageSearch, List<GoogleImageObject>> {
}