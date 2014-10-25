package com.bustiblelemons.async;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Pair;

import java.util.concurrent.Executor;

public abstract class AbsSimpleAsync<P, R extends Object> extends AsyncTask<P, Pair<P, R>, R> {

    protected Context  context;
    private   Executor executor;

    public AbsSimpleAsync(Context context) {
        this.context = context;
    }

    protected abstract R call(P... params) throws Exception;

    protected abstract boolean onException(Exception e);

    protected boolean onSuccess(R result) {
        return false;
    }


    public Context getContext() {
        return context;
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

    public void publishProgress(P param, R result) {
        publishProgress(Pair.create(param, result));
    }

    protected abstract void onProgressUpdate(P param, R result);

    @Override
    protected void onProgressUpdate(Pair<P, R>... values) {
        super.onProgressUpdate(values);
        for (Pair<P, R> v : values) {
            if (v != null) {
                onProgressUpdate(v.first, v.second);
            }
        }
    }

    @Override
    protected void onPostExecute(R result) {
        super.onPostExecute(result);
    }

    public AsyncTask<P, Pair<P, R>, R> executeCrossPlatform(P... params) {
        return executeCrossPlatform(getExecutor(), params);
    }

    public AsyncTask<P, Pair<P, R>, R> executeCrossPlatform(Executor executor, P... params) {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB ?
                this.executeOnExecutor(executor, params) : this.execute(params);
    }

    private Executor getExecutor() {
        return executor == null ?
                executor = ExecutorsProvider.getExecutor(getClass(), getExecutorType()) : executor;
    }

    protected ExecutorsProvider.Type getExecutorType() {
        return ExecutorsProvider.Type.SINGLE;
    }
}
