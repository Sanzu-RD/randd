package com.souchy.randd.commons.diamond.statics.stats.properties.creatures;

import java.util.ArrayList;
import java.util.List;

public class Visibility {
	
	private List<Object> hiders = new ArrayList<>();
	
	public boolean isVisible() {
		return size() == 0;
	}
	
	public int size() {
		return hiders.size();
	}
	
	public void add(Object o) {
		hiders.add(o);
	}
	
	public void remove(Object o) {
		hiders.remove(o);
	}
	
}
