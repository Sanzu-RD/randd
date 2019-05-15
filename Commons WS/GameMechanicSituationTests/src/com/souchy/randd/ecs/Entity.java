package com.souchy.randd.ecs;

import java.util.Map;

public interface Entity {
	
	//public int ID = 0;
	//public Map<Class<?>, Component> components = new HashMap<>();
	
	public int getID();
	public Map<Class<?>, Component> getComponents();
	
}
