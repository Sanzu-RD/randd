package com.souchy.randd.ecs.imp;

import com.souchy.randd.ecs.Component;
import com.souchy.randd.ecs.Entity;

/**
 * Component to hold a reference to a parent entity
 * 
 * @author Plants
 *
 */
public class ParentComponent implements Component {
	
	/**
	 * Parent reference
	 */
	public Entity parent;
	
	public ParentComponent(Entity parent) {
		this.parent = parent;
	}
	
	@Override
	public ParentComponent copy() {
		var p = new ParentComponent(parent);
		return p;
	}
	
}
