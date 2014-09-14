package com.bustiblelemons.patterns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 14.09.14.
 */
public abstract class ObservedObjectImpl<T> implements ObservedObject<T, ObjectObserver<T>> {

    private final Object MUTEX = new Object();
    private List<ObjectObserver<T>> observers;
    private String                  message;
    private boolean                 changed;

    public ObservedObjectImpl() {
        this.observers = new ArrayList<ObjectObserver<T>>();
    }

    @Override
    public void register(ObjectObserver<T> obj) {
        if (obj == null) { throw new NullPointerException("Null Observer"); }
        if (!observers.contains(obj)) { observers.add(obj); }
    }

    @Override
    public void unregister(ObjectObserver<T> obj) {
        observers.remove(obj);
    }

    @Override
    public void notifyObservers() {
        notifyObservers(null);
    }

    @Override
    public void notifyObservers(T... data) {
        for (T d : data) {
            List<ObjectObserver<T>> observersLocal = null;
            //synchronization is used to make sure any observer registered after message is received is not notified
            synchronized (MUTEX) {
                if (!changed) { return; }
                observersLocal = new ArrayList<ObjectObserver<T>>(this.observers);
                this.changed = false;
            }
            for (ObjectObserver<T> obj : observersLocal) {
                obj.update(d);
            }
        }
    }

    @Override
    public Object getUpdate(ObjectObserver<T> obj) {
        return this.message;
    }
}
