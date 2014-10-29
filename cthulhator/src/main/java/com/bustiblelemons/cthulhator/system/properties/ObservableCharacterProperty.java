package com.bustiblelemons.cthulhator.system.properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Observer;

/**
 * Created by hiv on 29.10.14.
 */
public class ObservableCharacterProperty {

    private List<OnCharacterPropertChanged> observers;
    private boolean                         changed;
    private OnCharacterPropertChanged       onCharacterPropertChanged;

    public void registerObserver(OnCharacterPropertChanged observer) {
        if (observer == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (!observers.contains(observer))
                observers.add(observer);
        }
    }

    /**
     * Clears the changed flag for this {@code Observable}. After calling
     * {@code clearChanged()}, {@code hasChanged()} will return {@code false}.
     */
    protected void clearChanged() {
        changed = false;
    }

    /**
     * Returns the number of observers registered to this {@code Observable}.
     *
     * @return the number of observers.
     */
    public int countObservers() {
        return observers.size();
    }

    /**
     * Removes the specified observer from the list of observers. Passing null
     * won't do anything.
     *
     * @param observer the observer to remove.
     */
    public synchronized void deleteObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Removes all observers from the list of observers.
     */
    public synchronized void deleteObservers() {
        observers.clear();
    }

    /**
     * Returns the changed flag for this {@code Observable}.
     *
     * @return {@code true} when the changed flag for this {@code Observable} is
     * set, {@code false} otherwise.
     */
    public boolean hasChanged() {
        return changed;
    }

    /**
     * If {@code hasChanged()} returns {@code true}, calls the {@code update()}
     * method for every Observer in the list of observers using the specified
     * argument. Afterwards calls {@code clearChanged()}.
     */
    @SuppressWarnings("unchecked")
    public void notifyObservers() {
        int size = 0;
        OnCharacterPropertChanged[] arrays = null;
        synchronized (this) {
            if (hasChanged()) {
                clearChanged();
                size = observers.size();
                arrays = new OnCharacterPropertChanged[size];
                observers.toArray(arrays);
            }
        }
        if (arrays != null) {
            for (OnCharacterPropertChanged observer : arrays) {
                observer.onCharacterPropertChanged(this);
            }
        }
    }

    /**
     * Sets the changed flag for this {@code Observable}. After calling
     * {@code setChanged()}, {@code hasChanged()} will return {@code true}.
     */
    protected void setChanged() {
        changed = true;
    }

    @JsonIgnoreProperties
    public interface OnCharacterPropertChanged {
        <T extends ObservableCharacterProperty> void onCharacterPropertChanged(T property);
    }
}
