package com.bustiblelemons.async;

import android.util.SparseArray;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by bhm on 19.07.14.
 */
public class ExecutorsProvider {

    public enum Type {
        SINGLE, CACHED
    }

    public static Executor getExecutor(Class<?> clss) {
        return getExecutor(clss, Type.SINGLE);
    }

    public static Executor getExecutor(Class<?> clss, Type type) {
        return getExecutor(clss.getSimpleName().hashCode(), type);
    }

    private static SparseArray<Executor> executors = new SparseArray<Executor>();

    public static Executor getExecutor(int hashCode, Type type) {
        if (executors.get(hashCode) == null) {
            Executor _executor;
            if (type.equals(Type.SINGLE)) {
                _executor = Executors.newSingleThreadExecutor();
            } else {
                _executor = Executors.newCachedThreadPool();
            }
            executors.put(hashCode, _executor);
        }
        return executors.get(hashCode);
    }
}
