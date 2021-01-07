package com.souchy.randd.commons.diamond.statics;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.diamond.statics.stats.properties.StatProperty;
import com.souchy.randd.commons.diamond.statics.stats.properties.StatProperty.StatPropertyID;

public interface Element extends StatProperty {
	
	public static List<Element> values = new ArrayList<>();
	
	/**
	 * Global affinity
	 */
	public static Element global = new Element() {
		{
			StatPropertyID.register(this);
			values.add(this);
		}
		
		public String name() {
			return "global";
		}
	};
	public String name();
	
	public static int count() {
		return values.size();
	}
	
}
