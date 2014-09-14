package com.bustiblelemons.patterns;


/**
 * Created by bhm on 14.09.14.
 */
public interface ObservedObject<T, O extends ObjectObserver<T>> {
    void register(O obj);

    void unregister(O obj);

    void notifyObservers();

    void notifyObservers(T... data);

    Object getUpdate(O obj);
}
