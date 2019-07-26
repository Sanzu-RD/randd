package gamemechanics.status;

import java.util.List;

import data.new1.Effect;
import gamemechanics.common.Disposable;
import gamemechanics.models.entities.Entity;


public abstract class Status implements Disposable {
	
	public Entity source;
	public Entity target;
	
	public List<Effect> effects;
	
	
	public int stacks;
	public int duration;
	public boolean canRemove;
	public boolean canDebuff;
	
	
	
	public Status() { //Entity source, Entity target) {
		source.fight.bus.register(this);
	}

	
//	@Subscribe
//	public abstract <T extends FightEvent> void register(T e, Entity sauce, Entity target);
	
	
	/** override fuse behaviour to affect stacks count, duration, both, or neither */
	public abstract void fuse(Status s);
	
	public void stackAdd(int stacks) {
		this.stacks += stacks;
	}
	public void stackSet(int stacks) {
		this.stacks = stacks;
	}
	public void refreshAdd(int duration) {
		this.duration += duration;
	}
	public void refreshSet(int duration) {
		this.duration = duration;
	}
	
	/**  */
	//public abstract Status copy();
	
	
	/*
	// dont need those if u code Statuses because u dont event need to check if an entity has the status if they just automatically work on events. example invulnerable 
	public static enum States {
		Invulnerable,
		Inoffensive,
		Untargetable,
		Invisible,
		ClearMind,
		LightMind,
		LightFeet,
		Heavy,
		Grounded,
		Rooted,
		Muted,
	}
	 */
	
	
	
	

	@Override
	public void dispose() {
		source = null;
		target = null;
		
		effects = null;
	}
	
	
}
