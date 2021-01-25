package com.souchy.randd.commons.diamond.systems;

import com.souchy.randd.commons.diamond.models.Effect;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.ecs.Family;

public class EffectSystem extends Family<Effect> { 

	public EffectSystem(Engine engine) {
		super(engine, Effect.class);
	}

	@Override
	public void update(float delta) {
//		Log.info("diamond effect system update size: " + this.size());
//		foreach(s -> {
			
//		});
	}
	
	public Effect get(int instanceid) {
		return first(s -> s.id == instanceid);
	}
	
}
