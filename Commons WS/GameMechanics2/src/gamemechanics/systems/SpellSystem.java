package gamemechanics.systems;

import data.new1.ecs.Engine;
import data.new1.ecs.Family;
import gamemechanics.models.Spell;

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
