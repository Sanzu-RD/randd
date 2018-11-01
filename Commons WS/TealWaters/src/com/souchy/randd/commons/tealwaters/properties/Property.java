package com.souchy.randd.commons.tealwaters.properties;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Property<T> {

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }
    
	
	private Supplier<T> getter;
	private Consumer<String> setter;

	public Property(Supplier<T> getter, Consumer<String> setter) {
		this.getter = getter;
		this.setter = setter;
	}

	public T get() {
		return getter.get();
	}

	public void set(T val) {
        T oldValue = this.get();
		setter.accept(val.toString());
        this.pcs.firePropertyChange("value", oldValue, val);
	}

}
