package com.souchy.randd.situationtest.properties;

import com.souchy.randd.situationtest.properties.types.StatProperties;

public class StatProperty {
	public final StatProperties type;
	public int value;
	public StatProperty(StatProperties type, int value) {
		this.type = type;
		this.value = value;
	}
	
	public StatProperty copy() {
		return new StatProperty(type, value);
	}
	
	public String toString() {
		return "[" + type + ", " + value + "]";
	}
}