package com.hiddenpiranha.commons.tealwaters.properties;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Property {

	private Supplier<String> getter;
	private Consumer<String> setter;

	public Property(Supplier<String> getter, Consumer<String> setter) {
		this.getter = getter;
		this.setter = setter;
	}

	public String get(){
		return getter.get();
	}
	public void set(String val){
		setter.accept(val);
	}
	
}
