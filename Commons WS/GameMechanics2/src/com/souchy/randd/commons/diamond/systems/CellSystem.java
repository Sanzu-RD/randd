package com.souchy.randd.commons.diamond.systems;

import com.souchy.randd.commons.diamond.common.ecs.Engine;
import com.souchy.randd.commons.diamond.common.ecs.Family;
import com.souchy.randd.commons.diamond.models.Cell;

public class CellSystem extends Family<Cell> { 

	public CellSystem(Engine engine) {
		super(engine, Cell.class);
	}
	
	@Override
	public void update(float delta) {
//		Log.info("diamond cell system update");
		foreach(c -> {
			
		});
	}
	
	
}
