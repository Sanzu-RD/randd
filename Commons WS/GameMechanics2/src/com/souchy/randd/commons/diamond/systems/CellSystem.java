package com.souchy.randd.commons.diamond.systems;

import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.ecs.Family;

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

	public Cell get(int instanceid) {
		return first(s -> s.id == instanceid);
	}
	
}
