package com.bustiblelemons.async;

import java.util.concurrent.Executor;


/**
 * @author jacek on 8/2/14 12:57
 * @origin io.github.scottmaclure.character.traits.network.api.asyn
 */
public class AsyncInfo<P, R> {
    private Class<?>               clss;
    private Executor               executor;
    private ExecutorsProvider.Type type;

    public Class<?> getClss() {
        return clss;
    }

    public AsyncInfo<P, R> setClss(Class<?> clss) {
        this.clss = clss;
        return this;
    }

    public Executor getExecutor() {
        return executor;
    }

    public AsyncInfo<P, R> setExecutor(Executor executor) {
        this.executor = executor;
        return this;
    }

    public ExecutorsProvider.Type getType() {
        return type;
    }

    public AsyncInfo<P, R> setExectutorType(ExecutorsProvider.Type type) {
        this.type = type;
        return this;
    }
}
