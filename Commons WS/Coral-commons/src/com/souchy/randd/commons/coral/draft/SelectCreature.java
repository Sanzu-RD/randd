package com.souchy.randd.commons.coral.draft;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.jade.matchmaking.Team;

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
	 * To what team should the creature be added.
	 * This is only for mocking purposes to manually select creatures on both teams 
	 * Normal draft/blind players can only select for themselves
	 * 
	 * Coral will ignore the team if the user is not Creator level
	 */
	public Team team = Team.A;
	
	/**
	 * pick or ban turn for the server to tell the client where the pick goes
	 */
	public int turn;

	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(modelid);
		out.writeInt(team.ordinal());
		out.writeInt(turn);
		return out;
	}

	@Override
	public SelectCreature deserialize(ByteBuf in) {
		modelid = in.readInt();
		team = Team.values()[in.readInt()];
		turn = in.readInt();
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
