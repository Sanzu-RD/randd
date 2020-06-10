package gamemechanics.models.entities;

import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;

import data.new1.spellstats.CreatureStats;
import data.new1.spellstats.Targetting;
import data.new1.timed.StatusList;
import gamemechanics.common.generic.Vector2;
import gamemechanics.components.Position;
import gamemechanics.events.new1.EventPipeline;
import gamemechanics.main.DiamondModels;
import gamemechanics.models.Fight;
import gamemechanics.models.FightEntity;
//import gamemechanics.statics.properties.Targeting;
import gamemechanics.statics.properties.Targetability;
import io.netty.buffer.ByteBuf;

public abstract class Entity extends FightEntity implements BBSerializer, BBDeserializer {
	
	public static class EntityRef extends FightEntity {
		public int id;
		public EntityRef(Fight f, Entity e) {
			super(f);
			this.id = e.id;
		}
		public EntityRef(Fight f, int id) {
			super(f);
			this.id = id;
		}
		public Entity get() {
			return fight.entities.get(id);
		}
	}

	public static enum Team {
		A,
		B,
		/** C is Neutral */
		C,
	}
	
	/**
	 * entity id for identification and mostly retrival during deserialization
	 */
	public int id;

	
	/** filled and emptied by StatusAdd and StatusLose effects */
	public EventPipeline handlers;
	
	/** board position */
	public Position pos;

	/** Statuses */
	private StatusList statuses;
	
	/** Properties like pathing,  line of sights, ~~visibility~~, orientation */
	public Targetting targeting;
	
	public Entity(Fight f) {
		super(f);
		statuses = new StatusList(f);
		targeting = new Targetting(); // should be in stats
		handlers = new EventPipeline();
	}
	
	/**
	 * If this entity can cast through an entity
	 */
	public boolean canCastThrough(Entity e) {
		return targeting.can(Targetability.CanCastThroughBlocks) || e.targeting.can(Targetability.CanBeCastedThrough);
	}
	/**
	 * If this entity can target cast on an entity
	 */
	public boolean canCastOn(Entity e) {
		return targeting.can(Targetability.CanCastOnBlocks) || e.targeting.can(Targetability.CanBeCastedOn);
	}
	/**
	 * If this entity can walk through an entity (without stopping on it)
	 */
	public boolean canWalkThrough(Entity e) {
		return targeting.can(Targetability.CanWalkThroughBlocks) || e.targeting.can(Targetability.CanBeWalkedThrough);
	}
	/**
	 * If this entity can target walk and stop on an entity
	 */
	public boolean canWalkOn(Entity e) {
		return targeting.can(Targetability.CanWalkOnBlocks) || e.targeting.can(Targetability.CanBeWalkedOn);
	}
	public boolean isVisible() {
		return this.getStats().visible.value();
	}
	
	
	/**
	 * Default empty stat table.
	 * Override for creatures
	 */
	public CreatureStats getStats() {
		return new CreatureStats();
	}
	
	/**
	 * Default empty status table. 
	 * Override for creatures & cells
	 */
	public StatusList getStatus() {
		return statuses; //new StatusList();
	}
	
//	public Vector2 pos() {
//		return get(Position.class);
//	}
//	public void setPos(double x, double y) {
//		add(new Position(x, y));
//	}
//	public void setPos(Position pos) {
//		add(pos);
//	}
	@Override
	public <T> T get(Class<T> c) {
		if(c == Position.class)
			return (T) this.pos;
		return super.get(c);
	}
	
	public Cell getCell() {
		return fight.board.cells.get(pos.x, pos.y);
	}
	
	
	public EntityRef ref() {
		return new EntityRef(this.fight, this.id);
	}

	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(id);
		out.writeDouble(this.pos.x);
		out.writeDouble(this.pos.y);
		out.writeInt(this.team.ordinal());
		this.statuses.serialize(out);
		return null;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		this.id = in.readInt();
		double x = in.readDouble();
		double y = in.readDouble();
		this.pos = new Position(x, y);
		this.team = Team.values()[in.readInt()];
		this.statuses.deserialize(in);
		return null;
	}
	
	
}
