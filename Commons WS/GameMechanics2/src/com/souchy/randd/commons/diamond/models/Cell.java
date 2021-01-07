package com.souchy.randd.commons.diamond.models;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.models.stats.special.HeightStat;
import com.souchy.randd.commons.diamond.models.stats.special.Targetting;
import com.souchy.randd.commons.diamond.statics.filters.Height;
import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;
import com.souchy.randd.commons.tealwaters.ecs.Entity;

import io.netty.buffer.ByteBuf;

public class Cell extends Entity implements BBSerializer, BBDeserializer {

	private static int idCounter = 0;
	
	/**
	 * entity id for identification and mostly retrival during deserialization
	 */
	public int id;
	
	/** filled and emptied by StatusAdd and StatusLose effects */
//	public EventPipeline handlers;
	
	/** board position */
	public Position pos;

	/** Statuses */
	public StatusList statuses;
	
	/** Properties like pathing,  line of sights, ~~visibility~~, orientation */
	public Targetting targeting;
	
	// main creature on the cell (ex si elle porte ou mange un autre creature (ex tahm kench/pandawa))
//	public List<Creature> creatures;
	
	
	public Cell(Fight f, int x, int y) {
		super(f);
		this.id = idCounter++;
		this.pos = new Position(x, y);
//		this.creatures = new ArrayList<Creature>();
		this.targeting = new Targetting();
		add(targeting);
	}
	
	/**
	 * Checks if there's any creatures on this cell
	 */
	public boolean hasCreature() {
		return get(Fight.class).creatures.any(c -> c.pos.same(this.pos));
	}
	/**
	 * returns the first creature on the cell, or null if there is none
	 */
	public Creature getFirstCreature() {
		return get(Fight.class).creatures.first(c -> c.pos.same(this.pos));
	}
	/** 
	 * returns the first creature on the cell that is on the specified height, or null if there is none
	 */
	public Creature getCreature(Height h) {
		return get(Fight.class).creatures.first(c -> c.pos.same(this.pos) && c.stats.height.has(h));
	}
	/** 
	 * returns the first creature on the cell that is on the specified height, or null if there is none
	 */
	public Creature getCreature(HeightStat h) {
		return getCreature(h.single());
	}
	/** 
	 * returns all creatures on this cell
	 */
	public List<Creature> getCreatures() {
		return get(Fight.class).creatures.where(c -> c.pos == this.pos);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Class<T> c) {
		if(c == Position.class) return (T) this.pos;
		return super.get(c);
	}


	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(id);
		out.writeDouble(pos.x);
		out.writeDouble(pos.y);
		
		// status 
		this.statuses.serialize(out);
		// targeting
		this.targeting.serialize(out);
		
		return out;
	}


	@Override
	public BBMessage deserialize(ByteBuf in) {
		id = in.readInt();
		pos = new Position(in.readDouble(), in.readDouble());

		// status
		this.statuses = new StatusList(null);
		this.statuses.deserialize(in);
		
		// targeting
		this.targeting = new Targetting();
		this.targeting.deserialize(in);
		
		return null;
	}
	
	
}