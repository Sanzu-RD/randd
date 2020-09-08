package com.souchy.randd.commons.diamond.models;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.models.stats.Targetting;
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
	public List<Creature> creatures;
	
	
	public Cell(Fight f, int x, int y) {
		super(f);
		this.id = idCounter++;
		this.pos = new Position(x, y);
		this.creatures = new ArrayList<Creature>();
		this.targeting = new Targetting();
		add(targeting);
	}
	
	
	public boolean hasCreature() {
		return !getCreatures().isEmpty();
	}
	public List<Creature> getCreatures() {
//		for(Creature c : fight.timeline) {
//			if(c.getCell() == this) return c;
//		}
//		return null;
		return creatures;
	}

	
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
		
		// TODO serialize status ids, creatures ids, targeting
		return out;
	}


	@Override
	public BBMessage deserialize(ByteBuf in) {
		id = in.readInt();
		pos = new Position(in.readDouble(), in.readDouble());

		// TODO deserialize status ids, creatures ids, targeting
		return null;
	}
	
	
}