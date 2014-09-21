package io.github.scottmaclure.character.traits.asyn;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Pair;

import java.util.concurrent.Executor;

import io.github.scottmaclure.character.traits.logging.Logger;

public abstract class AbsAsynTask<P, R> extends AsyncTask<P, Pair<P, R>, R> {
    protected Context context;
    private Logger log = new Logger(AbsAsynTask.class);
    private Executor  executor;
    private Exception exception;

    public AbsAsynTask(Context context) {
        this.context = context;
    }

    public AsyncInfo<P, R> getInfo() {
        return new AsyncInfo<P, R>()
                .setClss(getClass())
                .setExecutor(getExecutor())
                .setExectutorType(getExecutorType());
    }

    protected abstract R call(P... params) throws Exception;

    protected abstract boolean onException(Exception e);

    protected boolean onSuccess(R result) {
        return result != null;
    }


    public Context getContext() {
        return context;
    }

    @Override
    protected R doInBackground(P... params) {
        try {
            return call(params);
        } catch (Exception e) {
            this.exception = e;
            onException(e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(R result) {
        if (this.exception != null) {
            log.e(exception);
            onException(exception);
            return;
        }
        onSuccess(result);
    }

    public void publishProgress(P param, R result) {
        publishProgress(Pair.create(param, result));
    }

    @Override
    protected void onProgressUpdate(Pair<P, R>... values) {
        super.onProgressUpdate(values);
        for (Pair<P, R> pair : values) {
            onProgressUpdate(pair.first, pair.second);
        }
    }

    public abstract void onProgressUpdate(P param, R result);

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
