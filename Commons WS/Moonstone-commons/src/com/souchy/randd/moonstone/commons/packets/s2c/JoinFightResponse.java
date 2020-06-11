package com.souchy.randd.moonstone.commons.packets.s2c;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import data.new1.timed.Status;
import gamemechanics.main.DiamondModels;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;
import gamemechanics.models.Fight;
import gamemechanics.models.Spell;
import io.netty.buffer.ByteBuf;

@ID(id = 11001)
public class JoinFightResponse implements BBMessage {

	public boolean accepted;
	
//	public Fight fight;
	public List<Cell> cells = new ArrayList<>();
	public List<Creature> creatures = new ArrayList<>();
	public List<Status> status = new ArrayList<>();
	public List<Spell> spells = new ArrayList<>();
	
	public JoinFightResponse() {
		
	}
	
	public JoinFightResponse(boolean accepted, Fight fight) {
		this.accepted = accepted;
		if(fight != null) {
			this.cells = fight.cells.family;
			this.creatures = fight.creatures.family;
			this.status = fight.status.family;
			this.spells = fight.spells.family;
		}
	}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeBoolean(accepted);
		
		out.writeInt(cells.size());
		cells.forEach(c -> c.serialize(out));
		
		out.writeInt(creatures.size());
		creatures.forEach(c -> c.serialize(out));
		
		out.writeInt(status.size());
		status.forEach(c -> c.serialize(out));
		
		out.writeInt(spells.size());
		spells.forEach(c -> c.serialize(out));
		
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		// maybe I should have a static reference to a fight just for deserialization???? something's weird
		this.accepted = in.readBoolean();
		int cellcount = in.readInt();
		int creaturecount = in.readInt();
		int statuscount = in.readInt();
		int spellcount = in.readInt();
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
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new JoinFightResponse(false, null);
	}

	@Override
	public int getBufferCapacity() {
		return 1;
	}

}