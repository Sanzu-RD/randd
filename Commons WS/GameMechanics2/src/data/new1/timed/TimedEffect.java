package data.new1.timed;

import data.new1.Effect;
import gamemechanics.common.Disposable;
import gamemechanics.models.entities.Entity;

/**
 * 
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

	
	/**
	 * override fuse behaviour to affect stacks count, duration, both, or neither. 
	 * @param s
	 * @return True if fused, false otherwise
	 */
	public abstract boolean fuse(TimedEffect s);
	
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
