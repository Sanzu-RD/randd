package data.new1.timed;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;

import data.new1.Effect;
import gamemechanics.common.generic.Disposable;
import gamemechanics.models.Fight;
import gamemechanics.models.FightObject;
import gamemechanics.models.entities.Entity;
import gamemechanics.models.entities.Entity.EntityRef;

/**
 * 
 * How to make a status : <br>
 * 
 * 1. Create a subclass of status  <br>
 * 2. Implement its fusion() policy <br>
 * 3. Implement any event handler so it can react to events (ex : "implements OnCanCastActionHandler")
 * 
 *
 * FIXME : have flags to tell if this status applies to cells (terrain effect (glyph/trap..)) or applies directly to creatures
 * 
 * FIXME : every terrain effect needs multiple handlers to react to OnWalk, OnEnter, OnLeave, and differences between walk/tp/dash/push
 *  
 * FIXME : would need a common reactor on every cell for OnAddStatusEvent to apply new terrain effects (glyphs) to creatures that are in the aoe on apply
 *  		
 * FIXME : terrain effects need a texture to display on the ground (9 patch texture for every cell and a special texture for the center)
 * 
 * FIXME : statuses should have an icon and i18n for name + description
 * 
 * @author Souchy
 *
 */
public abstract class Status extends FightObject /* extends TimedEffect */ implements Disposable, BBSerializer, BBDeserializer  {

	public static abstract class Passive extends Status {
		public Passive(Entity source) {
			super(source.fight, source.ref(), source.ref());
			this.canDebuff = false;
			this.canRemove = false;
		}
		@Override 
		public boolean fuse(Status s) {
			// no fusion
			return false;
		}
	}

	/**
	 * status model id
	 */
	public abstract int modelID();
	/**
	 * Create an instance of the status model implementation (ex Shocked, Burning) for deserialisation
	 */
	public abstract Status create(EntityRef source, EntityRef target);
	
	
	public EntityRef source;
	public EntityRef target;
	public Effect parent;
	
	public int stacks;
	public int duration;
	public boolean canRemove;
	public boolean canDebuff;
	
	/** this or a toString() / description() ? */
	public List<Effect> tooltipEffects = new ArrayList<>();

	
	public Status(Fight f, EntityRef source, EntityRef target) {
		super(f);
		this.source = source;
		this.target = target;
		//source.fight.bus.register(this);
	}
	
//	public Status(Fight fight, int sourceid, int targetid) {
//		this(new EntityRef(fight, sourceid), new EntityRef(fight, targetid));
//	}
//	public Status(Entity source, Entity target) {
//		this(source.ref(), target.ref());
//	}

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
		return Integer.toString(this.modelID());
	}

	@Override
	public void dispose() {
		source = null;
		target = null;
		parent = null;
	}
}
