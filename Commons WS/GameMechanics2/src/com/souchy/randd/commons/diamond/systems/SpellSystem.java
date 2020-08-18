package com.souchy.randd.commons.diamond.systems;

import com.souchy.randd.commons.diamond.common.ecs.Engine;
import com.souchy.randd.commons.diamond.common.ecs.Family;
import com.souchy.randd.commons.diamond.models.Spell;

public class SpellSystem extends Family<Spell> { 

	public SpellSystem(Engine engine) {
		super(engine, Spell.class);
	}

	@Override
	public void update(float delta) {
//		Log.info("diamond cell system update");
		foreach(s -> {
			
		});
	}
	
	public Spell get(int instanceid) {
		return first(s -> s.id == instanceid);
	}
	
}
