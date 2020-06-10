package gamemechanics.models;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;

import data.new1.ecs.Entity;
import data.new1.spellstats.Targetting;
import data.new1.timed.StatusList;
import gamemechanics.common.generic.Vector2;
import gamemechanics.components.Position;
import gamemechanics.events.new1.EventPipeline;
import gamemechanics.main.DiamondModels;
import io.netty.buffer.ByteBuf;

public class Cell extends Entity implements BBSerializer, BBDeserializer {

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
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public BBMessage deserialize(ByteBuf in) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}