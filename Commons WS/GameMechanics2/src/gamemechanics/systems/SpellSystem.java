package gamemechanics.systems;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;

import data.new1.ecs.Engine;
import data.new1.ecs.Engine.AddEntityEvent;
import data.new1.ecs.Engine.RemoveEntityEvent;
import gamemechanics.models.Fight;
import gamemechanics.models.Spell;

public class SpellSystem extends data.new1.ecs.System {

	// SpellInstance
	public List<Spell> family = new ArrayList<>();
	
	public SpellSystem(Engine engine) {
		super(engine);
	}

	public void dispose() {
		super.dispose();
		family.clear();
	}
	
	@Override
	public void update(float delta) {
//		Log.info("diamond cell system update");

		synchronized (family) {
			family.forEach(e -> {
				
			});
		}
	}
	
	public Spell get(int instanceid) {
		for(var spell : family)
			if(spell.id == instanceid)
				return spell;
		return null;
	}
	
	@Subscribe
	public void onAddedEntity(AddEntityEvent event) {
		if(event.entity instanceof Spell && event.entity.get(Fight.class) != null) // models have no fight component and we dont want them
			family.add((Spell) event.entity);
	}
	@Subscribe
	public void onRemovedEntity(RemoveEntityEvent event) {
		if(event.entity instanceof Spell)
			family.remove(event);
	}
	
}
