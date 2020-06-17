package com.souchy.randd.commons.coral.draft;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.jade.mm.Team;

import io.netty.buffer.ByteBuf;

/**
 * When selecting/locking in a creature in champ select
 * 
 * @author Blank
 * @date 16 juin 2020
 */
@ID(id = 7010)
public class SelectCreature implements BBMessage {
	
	/**
	 * CreatureModel id
	 */
	public int modelid;
	
	/**
	 * To what team should the creature be added
	 * This is only for mocking purposes to manually select creatures on both teams 
	 * Normal draft/blind players can only select for themselves
	 * Coraline will have to always set the correct team
	 * Coral will ignore the team if the user is not Creator level
	 */
	public Team team = null;
	

	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(modelid);
		out.writeInt(team.ordinal());
		return out;
	}

	@Override
	public SelectCreature deserialize(ByteBuf in) {
		modelid = in.readInt();
		team = Team.values()[in.readInt()];
		return this;
	}

	@Override
	public SelectCreature create() {
		return new SelectCreature();
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}
	
}
