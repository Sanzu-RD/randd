package com.souchy.randd.commons.diamond.systems;

import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.ecs.Family;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class SpellSystem extends Family<Spell> { 

	public SpellSystem(Engine engine) {
		super(engine, Spell.class);
	}

	@Override
	public void update(float delta) {
//		Log.info("diamond spell system update size: " + this.size());
//		foreach(s -> {
			
//		});
	}
	
	public Spell get(int instanceid) {
		return first(s -> s.id == instanceid);
	}
	
}
