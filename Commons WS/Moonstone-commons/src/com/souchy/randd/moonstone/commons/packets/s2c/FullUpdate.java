package com.souchy.randd.moonstone.commons.packets.s2c;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.commons.tealwaters.logging.Log;

import io.netty.buffer.ByteBuf;

/**
 * 
 * @author Robyn Girardeau
 *
 */
@ID(id = 11004)
public class FullUpdate implements BBMessage {

	public List<Cell> cells = new ArrayList<>();
	public List<Creature> creatures = new ArrayList<>();
	public List<Status> status = new ArrayList<>();
	public List<Spell> spells = new ArrayList<>();
	public List<Integer> timeline = new ArrayList<>();
	public int currentIndex = 0;
	public int currentTurn = 0;
	
	public FullUpdate() { }
	public FullUpdate(Fight fight) {
		if(fight != null) {
			this.cells = fight.cells.copy();
			this.creatures = fight.creatures.copy();
			this.status = fight.status.copy();
			this.spells = fight.spells.copy();
			this.timeline = fight.timeline.copy();
			this.currentIndex = fight.timeline.index();
			this.currentTurn = fight.timeline.turn();
		}
	}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(cells.size());
		out.writeInt(creatures.size());
		out.writeInt(status.size());
		out.writeInt(spells.size());
		out.writeInt(timeline.size());
		out.writeInt(currentIndex);
		out.writeInt(currentTurn);
		
		Log.info("FullUpdate write : " + String.join(", ", cells.size()+"", creatures.size()+"", status.size()+"", spells.size()+"", timeline.size()+""));
		
		cells.forEach(c -> c.serialize(out));
		creatures.forEach(c -> c.serialize(out));
		status.forEach(c -> c.serialize(out));
		spells.forEach(c -> c.serialize(out));
		timeline.forEach(c -> out.writeInt(c));
		
		return out;
	}
	
	@Override
	public BBMessage deserialize(ByteBuf in) {
		int cellcount = in.readInt();
		int creaturecount = in.readInt();
		int statuscount = in.readInt();
		int spellcount = in.readInt();
		int timelinecount = in.readInt();
		this.currentIndex = in.readInt();
		this.currentTurn = in.readInt();
		
		Log.info("FullUpdate read : " + String.join(", ", cellcount+"", creaturecount+"", statuscount+"", spellcount+"", timelinecount+""));
		
		for(int i = 0; i < cellcount; i++) {
			var c = new Cell(null, 0, 0); // we dont have the fight object yet here... will need to set fight + register the entity in the engine later
			c.deserialize(in);
			cells.add(c);
		}
		for(int i = 0; i < creaturecount; i++) {
			var c = new Creature(null);
			c.deserialize(in);
			creatures.add(c);
		}
		for(int i = 0; i < statuscount; i++) {
			var modelid = in.readInt();
			var model = DiamondModels.statuses.get(modelid);
			var s = model.copy(null);
			s.deserialize(in);
			status.add(s);
		}
		for(int i = 0; i < spellcount; i++) {
			var modelid = in.readInt();
			var model = DiamondModels.spells.get(modelid);
			var s = model.copy(null);
			s.deserialize(in);
			spells.add(s);
		}
		for(int i = 0; i < timelinecount; i++) {
			timeline.add(in.readInt());
		}
		return this;
	}
	
	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new FullUpdate();
	}
	
	@Override
	public int getBufferCapacity() {
		return 0;
	}
	
}
