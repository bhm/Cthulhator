package com.bustiblelemons.patterns;

/**
 * Created by bhm on 14.09.14.
 */
public interface ObjectObserver<T> {
    public void update();

    public void update(T d);

}
