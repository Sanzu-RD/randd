package com.souchy.randd.commons.coral.draft;

import org.bson.types.ObjectId;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.jade.matchmaking.Team;

import io.netty.buffer.ByteBuf;

/**
 * When the timer is up or a player selected his pick/ban
 * 
 * @author Blank
 * @date 5 juill. 2020
 */
@ID(id = 7005)
@SuppressWarnings("deprecation")
public class ChangeTurn implements BBMessage {
	
	/**
	 * Current team turn
	 */
//	public Team team;
	public ObjectId playerid;
	
	public ChangeTurn() {
		
	}
	public ChangeTurn(ObjectId playerid) {
		this.playerid = playerid;
	}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		// out.writeInt(team.ordinal());
		out.writeInt(playerid.getTimestamp());
		out.writeInt(playerid.getMachineIdentifier());
		out.writeShort(playerid.getProcessIdentifier());
		out.writeInt(playerid.getCounter());
		return out;
	}
	
	@Override
	public ChangeTurn deserialize(ByteBuf in) {
		// team = Team.values()[in.readInt()];
		playerid = new ObjectId(in.readInt(), in.readInt(), in.readShort(), in.readInt());
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
