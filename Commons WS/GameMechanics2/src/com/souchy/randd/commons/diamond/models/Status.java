package com.souchy.randd.commons.diamond.models;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.effects.status.ModifyStatusEffect;
import com.souchy.randd.commons.diamond.effects.status.RemoveStatusEffect;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.commons.diamond.statusevents.Handler;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent.OnTurnStartHandler;
import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.ecs.Entity;
import com.souchy.randd.commons.tealwaters.logging.Log;

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
public abstract class Status extends Entity implements OnTurnStartHandler, Handler, BBSerializer, BBDeserializer {

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
	public abstract int modelid();
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
	public List<Effect> effects = new ArrayList<>();

	
	public Status(Fight f, int sourceEntityId, int targetEntityId) { // EntityRef source, EntityRef target
		super(f);
		this.id = idCounter++;
		this.sourceEntityId = sourceEntityId;
		this.targetEntityId = targetEntityId;
		
		// ne pas register les modèles
		//source.fight.bus.register(this);
//		f.statusbus.register(this);
	}
	
	@Override
	public void register(Engine engine) {
		super.register(engine);
		this.effects.forEach(e -> e.register(engine));
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
	public boolean fuse(Status s) {
		genericFuseStrategy(s, true, true);
		return true;
	}
	
	/**
	 * When THIS status is added to a statuslist
	 */
	public abstract void onAdd();
	/** 
	 * When THIS status is lost from a statuslist
	 */
	public abstract void onLose();
	
	/** create an instance of this status */
	public abstract Status copy(Fight f);

	/**
	 * Be careful not to use this on a status model because they dont have a fight reference. <br>
	 * Use status.copy(Fight fight) instead in those cases.
	 */
	public Status copy() {
		return this.copy(get(Fight.class));
	}

	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(modelid());
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
	
	/**
	 * helper to create and apply a ModifyStatusEffect for a basic fuse strategy
	 */
	protected void genericFuseStrategy(Status s, boolean stacks, boolean duration) {
		var fight = get(Fight.class);
		var mod = new ModifyStatusEffect(get(Fight.class), AoeBuilders.single.get(), TargetType.full.asStat(), this, stacks ? s.stacks : 0, duration ? s.duration : 0);
		var sourceCrea = fight.creatures.get(sourceEntityId);
		var targetCrea = fight.creatures.get(targetEntityId);
		mod.height.set(targetCrea.stats.height);
		mod.apply(sourceCrea, targetCrea.getCell());
	}

	/**
	 * Décrémente la duration au tour de la source.
	 * Un status spécial pourrait override ça pour spécifier le "lead" qu'il veut (décrémenter au tour du target ou autre)
	 */
	@Override
	public void onTurnStart(TurnStartEvent event) {
		// check que ça soit le tour de la SOURCE
		var fight = event.fight;
		int turnStartCreatureID = fight.timeline.get(event.index);
		if(sourceEntityId == turnStartCreatureID) {
			this.duration--;
			if(duration == 0) {
				expire();
			}
		}
	}
	
	/**
	 * Supprime le status
	 */
	protected void expire() {
		var f = get(Fight.class);
		var source = f.creatures.get(sourceEntityId);
		var target = f.creatures.get(targetEntityId);
		var e = new RemoveStatusEffect(f, AoeBuilders.single.get(), TargetType.full.asStat(), this);
		e.apply(source, target.getCell());
		e.dispose();
	}
	
	
}
