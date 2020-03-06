package data.new1.timed;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;

import data.new1.Effect;
import gamemechanics.common.generic.Disposable;
import gamemechanics.events.OnLifeDmgInstance;
import gamemechanics.events.OnCanCastActionCheck.OnCanCastActionHandler;
import gamemechanics.models.entities.Entity;

/**
 * 
 * How to make a status : <br>
 * 
 * 1. Create a subclass of status  <br>
 * 2. Implement its fusion() policy <br>
 * 3. Implement any event handler so it can react to events (ex : "implements OnCanCastActionHandler")
 * 
 *
 * FIXME : have flags to tell if this status is a terrain effect (glyph/trap..) or applies directly to creatures
 * FIXME : have flags to tell what happens if this status is a terrain effect and a creature walks in/out/inside/through or is already in it on creation
 * 			initial trigger : creature is already in the area when the terrain effect is created or removed
 *  		trigger actions : creature walks or teleports : from inside to outside, inside to inside, outside to inside
 *  		can this be placed on a hole/block/creature/terraineffect? what about the opposide?
 *  		
 *  
 * 
 * @author Souchy
 *
 */
public abstract class Status /* extends TimedEffect */ implements Disposable {

	public static abstract class Passive extends Status {
		public Passive(Entity source) {
			super(source, source);
			this.canDebuff = false;
			this.canRemove = false;
		}
		@Override 
		public boolean fuse(Status s) {
			// no fusion
			return false;
		}
	}

	public abstract int id();
	
	public Entity source;
	public Entity target;
	public Effect parent;
	
	public int stacks;
	public int duration;
	public boolean canRemove;
	public boolean canDebuff;
	
	/** this or a toString() / description() ? */
	public List<Effect> tooltipEffects = new ArrayList<>();
	
	public Status(Entity source, Entity target) {
		this.source = source;
		this.target = target;
		//source.fight.bus.register(this);
	}

	/**
	 * Fuse behaviour to affect stacks count, duration, both, or neither. 
	 * @return True if fused. False means it doesnt fuse so you have 2 instances of the status with each their own duration and stacks
	 */
	public abstract boolean fuse(Status s);
	
	/**
	 * When THIS status is added to a statuslist
	 */
	public abstract void onAdd();
	/** 
	 * When THIS status is lost from a statuslist
	 */
	public abstract void onLose();
	
	public String getIconName() {
		return Integer.toString(id());
	}

	@Override
	public void dispose() {
		source = null;
		target = null;
		parent = null;
	}
}
