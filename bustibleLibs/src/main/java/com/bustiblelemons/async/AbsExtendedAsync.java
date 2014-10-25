package com.bustiblelemons.async;

import android.content.Context;
import android.util.Pair;

/**
 * Created by bhm on 01.10.14.
 */
public abstract class AbsExtendedAsync<P, R1 extends Object, R2 extends Object>
        extends AbsSimpleAsync<P, Pair<R1, R2>> {
    public AbsExtendedAsync(Context context) {
        super(context);
    }

    public void publishProgress(P param, R1 ret1, R2 ret2) {
        publishProgress(param, Pair.create(ret1, ret2));
    }


    @Override
    protected void onProgressUpdate(P param, Pair<R1, R2> result) {
        R1 ret1 = null;
        R2 ret2 = null;
        if (result != null) {
            ret1 = result.first;
            ret2 = result.second;
        }
        onProgressUpdate(param, ret1, ret2);
    }

    public abstract void onProgressUpdate(P param, R1 ret1, R2 ret2);
}
