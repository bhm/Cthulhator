package com.bustiblelemons.async;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

import java.util.concurrent.Executor;

/**
 * Created 9 Dec 2013
 */
public abstract class SimpleAsync<P, R extends Object> extends AsyncTask<P, R, R> {

    protected Context context;

    protected abstract R call(P... params) throws Exception;

    protected abstract boolean onException(Exception e);

    protected abstract boolean onSuccess(R result);

    public SimpleAsync(Context context) {
        this.context = context;
    }

    @Override
    protected R doInBackground(P... arg0) {
        try {
            return call(arg0);
        } catch (Exception e) {
            onException(e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(R result) {
        super.onPostExecute(result);
    }

    public AsyncTask<P, R, R> executeCrossPlatform(P... params) {
        return executeCrossPlatform(getExecutor(), params);
    }

    public AsyncTask<P, R, R> executeCrossPlatform(Executor executor, P... params) {
        return VERSION.SDK_INT > VERSION_CODES.HONEYCOMB ?
                this.executeOnExecutor(executor, params) : this.execute(params);
    }

    private Executor executor;

    private Executor getExecutor() {
        return executor == null ?
                executor = ExecutorsProvider.getExecutor(getClass(), getExecutorType()) : executor;
    }

    protected ExecutorsProvider.Type getExecutorType() {
        return ExecutorsProvider.Type.SINGLE;
    }
}
