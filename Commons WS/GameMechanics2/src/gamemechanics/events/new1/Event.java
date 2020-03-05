package gamemechanics.events.new1;

import java.util.ArrayList;
import java.util.List;

import data.new1.Effect;
import gamemechanics.data.effects.damage.Damage;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

public abstract class Event {
	
	// public final boolean canBeIntercepted;
	public boolean intercepted;
	public Entity source;
	public Cell target;
	public Effect effect;
	/**
	 * List of handlers that already touched this event. Every handler can only be
	 * applied once on a same event
	 */
	public List<Object> markedHandlers = new ArrayList<>();
	/** 
	 * Level of copy starts at 0 and goes deeper <p>
	 * That way we know the caster handlers are at level 0 <br>
	 * And the regular target handlers are at level 1 <br>
	 * So we can differentiate between interceptors(caster, event) and interceptors(target, event) even if the target is the caster
	 */
	public int level = 0;
	
	public Event(Entity source, Cell target, Effect effect) {
		this.effect = effect;
		this.source = source;
		this.target = target;
	}
	
	public Event copy() {
		var event = copy0();
		event.intercepted = intercepted;
		event.markedHandlers.addAll(markedHandlers);
		event.level = level + 1;
		return event;
	}
	
	/**  */
	public abstract Event copy0();
	
}
