package com.souchy.randd.commons.coral.draft;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.jade.matchmaking.Team;

import io.netty.buffer.ByteBuf;

/**
 * WHen the timer is up or a player selected his pick/ban
 * 
 * @author Blank
 * @date 5 juill. 2020
 */
@ID(id = 7005)
public class ChangeTurn implements BBMessage {
	
	/**
	 * Current team turn
	 */
	public Team team;

	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(team.ordinal());
		return out;
	}

	@Override
	public ChangeTurn deserialize(ByteBuf in) {
		team = Team.values()[in.readInt()];
		return this;
	}

	@Override
	public ChangeTurn create() {
		return new ChangeTurn();
	}

	@Override
	public int getBufferCapacity() {
		return 1;
	}
	
}
