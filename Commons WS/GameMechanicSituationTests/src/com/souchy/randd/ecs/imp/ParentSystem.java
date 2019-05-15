package com.souchy.randd.ecs.imp;

import com.souchy.randd.ecs.System;
import com.souchy.randd.ecs.Entity;

public class ParentSystem implements System<ParentComponent> {


	@Override
	public Class<ParentComponent> getType() {
		return ParentComponent.class;
	}
	
	
}
