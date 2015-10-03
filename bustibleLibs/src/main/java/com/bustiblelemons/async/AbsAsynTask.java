package com.bustiblelemons.async;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Pair;

import com.bustiblelemons.logging.Logger;

import java.util.concurrent.Executor;

/**
 * @author jacek on 8/2/14 12:40
 * @origin com.bustiblelemons.async
 * TODO move to RX code soon
 */
public abstract class AbsAsynTask<P, R> extends AsyncTask<P, Pair<P, R>, R> {
    private Logger log = new Logger(AbsAsynTask.class);
    protected Context context;

    protected abstract R call(P... params) throws Exception;

    protected abstract boolean onException(Exception e);

    protected abstract boolean onSuccess(R result);

    public Context getContext() {
        return context;
    }

    public AbsAsynTask(Context context) {
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

    public void publishProgress(P param, R result) {
        onProgressUpdate(Pair.create(param, result));
    }

    @Override
    protected void onProgressUpdate(Pair<P, R>... values) {
        super.onProgressUpdate(values);
        for (Pair<P,R> pair : values) {
            onProgressUpdate(pair.first, pair.second);
        }
    }

    public abstract void onProgressUpdate(P param, R result);

    public AsyncTask<P, Pair<P,R>, R> executeCrossPlatform(P... params) {
        return executeCrossPlatform(getExecutor(), params);
    }

    public AsyncTask<P, Pair<P,R>, R> executeCrossPlatform(Executor executor, P... params) {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB ?
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
