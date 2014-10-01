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
    protected void onProgressUpdate(Pair<P, Pair<R1, R2>>... values) {
        super.onProgressUpdate(values);
        for (Pair<P, Pair<R1, R2>> v : values) {
            P param = null;
            if (v != null) {
                param = v.first;
            }
            R1 ret1 = null;
            R2 ret2 = null;
            if (v.second != null) {
                Pair<R1, R2> s = v.second;
                ret1 = s.first;
                ret2 = s.second;
            }
            onProgressUpdate(param, ret1, ret2);
        }
    }

    public abstract void onProgressUpdate(P param, R1 ret1, R2 ret2);
}
