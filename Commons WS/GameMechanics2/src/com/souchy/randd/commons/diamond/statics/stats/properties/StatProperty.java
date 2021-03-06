package com.souchy.randd.commons.diamond.statics.stats.properties;

import java.util.HashMap;
import java.util.Map;

public interface StatProperty {
	
	public static class StatPropertyID {
		private static int i = 1;
		private static Map<StatProperty, Integer> propertyIDs = new HashMap<>();
		public static int register(StatProperty s) {
			if(propertyIDs.containsKey(s) == false) {
				propertyIDs.put(s, i++); //(int) Math.pow(2, i++));
			}
			return get(s);
		}
		public static int get(StatProperty s) {
			return propertyIDs.get(s);
		}
	}
	
	public default int val() {
		return StatPropertyID.get(this);
	}
	
}
