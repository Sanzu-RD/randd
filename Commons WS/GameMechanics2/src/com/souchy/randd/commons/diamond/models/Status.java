package com.souchy.randd.commons.diamond.models;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.diamond.common.ecs.Entity;
import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;

import io.netty.buffer.ByteBuf;

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
public abstract class Status extends Entity implements BBSerializer, BBDeserializer {

//	public static abstract class Passive extends Status {
//		public Passive(Entity source) {
//			super(source.fight, source.ref(), source.ref());
//			this.canDebuff = false;
//			this.canRemove = false;
//		}
//		@Override 
//		public boolean fuse(Status s) {
//			// no fusion
//			return false;
//		}
//	}

	private static int idCounter = 0;
	
	/**
	 * status instance id
	 */
	public int id;
	/**
	 * status model id
	 */
	public abstract int modelID();
	/**
	 * Create an instance of the status model implementation (ex Shocked, Burning) for deserialisation
	 */
	public abstract Status create(Fight fight, int source, int target);
	
	
	public int sourceEntityId;
	public int targetEntityId;
	public int parentEffectId; //Effect parent;
	
	public int stacks;
	public int duration;
	public boolean canRemove;
	public boolean canDebuff;
	
	
	/** this or a toString() / description() ? */
//	public List<Effect> tooltipEffects = new ArrayList<>();
	public List<Integer> effects = new ArrayList<>();

	
	public Status(Fight f, int sourceEntityId, int targetEntityId) { // EntityRef source, EntityRef target
		super(f);
		this.id = idCounter++;
		this.sourceEntityId = sourceEntityId;
		this.targetEntityId = targetEntityId;
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
	
	/** create an instance of this status */
	public abstract Status copy(Fight fight);

	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(modelID());
		out.writeInt(id);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		this.id = in.readInt();
		// TODO Auto-generated method stub
		return null;
	}
	
//	public String getIconName() {
//		return Integer.toString(this.modelID());
//	}

//	@Override
//	public void dispose() {
//		super.dispose();
//		source = null;
//		target = null;
//		parentEffectId = null;
//	}
	
}
