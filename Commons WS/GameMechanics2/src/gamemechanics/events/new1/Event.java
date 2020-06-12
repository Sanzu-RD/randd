package gamemechanics.events.new1;

import java.util.ArrayList;
import java.util.List;

import data.new1.Effect;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;

/**
 * Effect event.
 * <p>
 * Events go through caster handlers and target handlers to allow intercepting or modifying the effects individually for each target.
 * They also allow handlers to react to each event
 * 
 * @author Blank
 * @date 5 mars 2020
 */
public abstract class Event {
	
	// public final boolean canBeIntercepted;
	public boolean intercepted;
	public Creature source;
	public Cell target;
	public Effect effect;
	/**
	 * List of handlers that already touched this event. Every handler can only be applied once on a same event and its children (copied down)
	 */
	public List<Handler> markedHandlers = new ArrayList<>();
	/** 
	 * Level of copy starts at 0 and goes deeper <p>
	 * That way we know the caster handlers are at level 0 <br>
	 * And the regular target handlers are at level 1 <br>
	 * So we can differentiate between interceptors(caster, event) and interceptors(target, event) even if the target is the caster
	 */
	public int level = 0;
	/**
	 * Children of this effect so that we can compile results at the end. <br>
	 * Ex: calculate the total of damage done in the aoe, or the total amount of mana reduced in the aoe...
	 */
	public List<Event> children = new ArrayList<>();
	
	public Event(Creature source, Cell target, Effect effect) {
		this.effect = effect;
		this.source = source;
		this.target = target;
	}
	
	public Event copy() {
		var event = copy0();
		event.intercepted = intercepted;
		event.markedHandlers.addAll(markedHandlers);
		event.level = level + 1;
		this.children.add(event); // add the copy to the parent's children
		return event;
	}
	
	/** Create a new instance of the event subclass with the source, target and effect forced by the ctor */
	public abstract Event copy0();
	
}
