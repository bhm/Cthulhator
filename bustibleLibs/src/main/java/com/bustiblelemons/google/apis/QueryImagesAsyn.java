package com.bustiblelemons.google.apis;

import android.content.Context;

import com.bustiblelemons.async.SimpleAsync;

/**
 * Created by bhm on 18.07.14.
 */
public class QueryImagesAsyn extends SimpleAsync<String, String> {

    public QueryImagesAsyn(Context context) {
        super(context);
    }

    @Override
    public String call(String... params) {
        return null;
    }

    @Override
    public boolean onException(Exception e) {
        return false;
    }

    @Override
    public boolean onSuccess(String result) {
        return false;
    }
}
