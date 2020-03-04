package gamemechanics.events.new1;

import java.util.ArrayList;
import java.util.List;

import data.new1.Effect;
import gamemechanics.data.effects.damage.Damage;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

public abstract class Event {
	
//	public final boolean canBeIntercepted;
	public boolean intercepted;
	
//	public Event() {
//		canBeIntercepted = false;
//	}
//	
//	public Event copy() {
//		var event = new Event();
//		event.canBeIntercepted = this.canBeIntercepted;
//		event.intercepted = this.intercepted;
//		return event;
//	}
//	
//	
//	/**
//	 * TODO What {{EVENT TYPE}} could even be non-interceptiple
//	 * @return
//	 */
//	public abstract boolean canBeIntercepted();
//	
	
	public Entity source;
	public Cell target;
	public Effect effect;
	
//	public Event parent;

	public Event(Entity source, Cell target, Effect effect) {
		this.effect = effect;
		this.source = source;
		this.target = target;
	}
	
	/**
	 * List of handlers that already touched this event. Every handler can only be applied once on a same event
	 */
	public List<Object> markedHandlers = new ArrayList<>();
	
	public Event copy() {
		var event = copy0();
		event.intercepted = intercepted;
		event.markedHandlers.addAll(markedHandlers);
//		event.parent = this;
		// v déjà forcé par le constructeur
//		event.source = source;
//		event.target = target;
//		event.effect = effect.copy();
		return event;
	}
	
	public abstract Event copy0();
	
}
