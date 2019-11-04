package data.new1.timed;

import data.new1.Effect;
import gamemechanics.common.Disposable;
import gamemechanics.models.entities.Entity;

/**
 * 
 * Statuses are different from Buffs.
 * Buffs give stats for the time of their duration.
 * Statuses react to events.
 * Both can be used to check a condition.
 * 
 * @author Blank
 * @date 27 oct. 2019
 */
public abstract class TimedEffect implements Disposable {

	public Entity source;
	public Entity target;
	public Effect parent;
	
	public int stacks;
	public int duration;
	public boolean canRemove;
	public boolean canDebuff;

	
	/** override fuse behaviour to affect stacks count, duration, both, or neither */
	public abstract void fuse(TimedEffect s);
	
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

	@Override
	public void dispose() {
		source = null;
		target = null;
		parent = null;
	}
}
