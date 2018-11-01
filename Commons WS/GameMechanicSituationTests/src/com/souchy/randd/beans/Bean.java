package com.souchy.randd.beans;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Bean<T> {

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }
    
    
    private T value;

    public T get() {
        return this.value;
    }

    public void set(T newValue) {
        T oldValue = this.value;
        this.value = newValue;
        this.pcs.firePropertyChange("value", oldValue, newValue);
    }
	
	
}
