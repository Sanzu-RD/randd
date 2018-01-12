package com.souchy.randd.commons.tealwaters.properties;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class PropertyFactory {


	public static Property create(Supplier<String> getter, Consumer<String> setter) {
		return new Property(getter, setter);
	}
	
	public static PropertyFactory createFactory(Supplier<String> getter, Consumer<String> setter) {
		return new PropertyFactory(getter, setter);
	}
	
	
	private Supplier<String> getter;
	private Consumer<String> setter;
	
	private PropertyFactory(Supplier<String> getter, Consumer<String> setter) {
		this.getter = getter;
		this.setter = setter;
	}
	
	public Property create() {
		return create(getter, setter);
	}
	
	
}
