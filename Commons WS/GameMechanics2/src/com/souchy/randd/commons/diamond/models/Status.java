package com.souchy.randd.commons.diamond.models;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.effects.status.ModifyStatusEffect;
import com.souchy.randd.commons.diamond.effects.status.RemoveStatusEffect;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.models.stats.CreatureStats;
import com.souchy.randd.commons.diamond.models.stats.special.HeightStat;
import com.souchy.randd.commons.diamond.statics.filters.Height;
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

	public CreatureStats creatureStats;
	
	public Status(Fight f, int sourceEntityId, int targetEntityId) { // EntityRef source, EntityRef target
		super(f);
		this.id = idCounter++;
		this.sourceEntityId = sourceEntityId;
		this.targetEntityId = targetEntityId;
		
		Log.format("Status new %s (%s), %s", this.id, this.modelid(), this, f);
		// ne pas register les modèles
		//source.fight.bus.register(this);
//		f.statusbus.register(this);
	}
	
//	public Status(Fight fight, int sourceid, int targetid) {
//		this(new EntityRef(fight, sourceid), new EntityRef(fight, targetid));
//	}
//	public Status(Entity source, Entity target) {
//		this(source.ref(), target.ref());
//	}

	/**
	 * Fuse behaviour to affect stacks count, duration, both, or neither. <p>
	 * 
	 * <code> 
	 * {@link Status#genericFuseStrategy(Status, boolean, boolean, boolean)}<br>
	 *  genericFuseStrategy(s, true, true, true); <br>
	 *	return true; 
	 *	</code><p>
	 * @return True if fused. False means it doesnt fuse so you have 2 instances of the status with each their own duration and stacks
	 */
	public boolean fuse(Status s) {
		genericFuseStrategy(s, true, true, true);
		return true;
	}
	
	/**
	 * When THIS status is added to a statuslist
	 */
	public void onAdd() {
//		var creature = this.get(Fight.class).creatures.get(targetEntityId);
//		creature.stats.
//		creature.stats.resistance.get(Element.global).more -= 50;
	}
	
	/** 
	 * When THIS status is lost from a statuslist
	 */
	public void onLose() {
		
	}
	
	
	public Status copy(Fight f) {
		var s = copy0(f); //create(f, sourceEntityId, targetEntityId);
		s.canDebuff = canDebuff;
		s.canRemove = canRemove;
		s.stacks = stacks;
		s.duration = duration;
		this.effects.forEach(e -> s.effects.add(e.copy()));
		if(creatureStats != null) s.creatureStats = creatureStats.copy(null);
		return s;
	}
	
	/** 
	 * create an instance of this status <br>
	 * 
	 * all generic things are already copied
	 */
	public abstract Status copy0(Fight f);

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
		out.writeInt(parentEffectId);
		out.writeInt(sourceEntityId);
		out.writeInt(targetEntityId);
		out.writeInt(stacks);
		out.writeInt(duration);
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		this.id = in.readInt();
		this.parentEffectId = in.readInt();
		this.sourceEntityId = in.readInt();
		this.targetEntityId = in.readInt();
		this.stacks = in.readInt();
		this.duration = in.readInt();
		return null;
	}
	
	/**
	 * helper to create and apply a ModifyStatusEffect for a basic fuse strategy
	 */
	protected void genericFuseStrategy(Status s, boolean stacks, boolean duration, boolean addOrRefresh) {
		var fight = get(Fight.class);
		int finalstacks = this.stacks;
		int finalduration = this.duration;
		if(stacks) 
			finalstacks = addOrRefresh ? finalstacks + s.stacks : 
				(s.stacks > this.stacks ? s.stacks : this.stacks); // refresh takes the highest number
		if(duration) 
			finalduration = addOrRefresh ? finalduration + s.duration : 
				(s.duration > this.duration ? s.duration : this.duration); // refresh takes the highest number
		
		applyFuseEffect(fight, finalstacks, finalduration);
	}
	
	protected void applyFuseEffect(Fight fight, int finalstacks, int finalduration) {
		try {
			var mod = new ModifyStatusEffect(AoeBuilders.single.get(), TargetType.full.asStat(), this, finalstacks, finalduration);
			var sourceCrea = fight.creatures.get(sourceEntityId);
			var targetCrea = fight.entities.get(targetEntityId);
			fight.board.get(targetCrea);
			HeightStat hhh = targetCrea instanceof Creature ? ((Creature) targetCrea).stats.height : Height.floor.stat();
			mod.height.set(hhh); // targetCrea.stats.height);
			mod.apply(sourceCrea, fight.board.get(targetCrea)); // targetCrea.getCell());
		} catch (Exception e) {
			Log.error("", e);
		}
	}

	/**
	 * Décrémente la duration au tour du target.
	 * Un status spécial pourrait override ça pour spécifier le "lead" qu'il veut (décrémenter au tour de la source ou autre)
	 */
	@Override
	public void onTurnStart(TurnStartEvent event) {
		// check que ça soit le tour de la SOURCE
		var fight = event.fight;
		int turnStartCreatureID = fight.timeline.get(event.index);
		//Log.format("Status onTurnStart, %s, %s", sourceEntityId, turnStartCreatureID);
		if(sourceEntityId == turnStartCreatureID) {
			this.duration--;
			Log.format("Status (%s) decrement duration %s %s", this.id, duration, this);
			if(duration <= 0) {
				expire();
			}
		}
	}
	
	/**
	 * Supprime le status
	 */
	protected void expire() {
		try {
			Log.format("Status (%s) expire %s %s", this.id, duration, this);
			var f = get(Fight.class);
			var source = f.creatures.get(sourceEntityId);
			var target = f.entities.get(targetEntityId);
			// var pos = target.get(Position.class);
			// var cell = f.board.get(pos);
			
			var e = new RemoveStatusEffect(AoeBuilders.single.get(), TargetType.full.asStat(), this);
			e.apply(source, f.board.get(target)); // target.getCell());
		} catch (Exception e) {
			Log.error("", e);
		}
	}
	
	/**
	 * Remove from the engine, systems and the statusbus
	 */
	@Override
	public void dispose() {
		var f = this.get(Fight.class);
		if(f != null && f.statusbus != null) 
			f.statusbus.unregister(this);
		super.dispose();
	}
	
}
