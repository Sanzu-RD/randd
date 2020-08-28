package com.souchy.randd.moonstone.commons.packets.c2s;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

/**
 * When a player presses pass turn, send this to the server.
 * 
 * @author Blank
 * @date 9 mai 2020
 */
@SuppressWarnings("exports")
@ID(id = 10003)
public class PassTurn implements BBMessage {
	
	public int turn, index;	

	public PassTurn() {
		
	}
	public PassTurn(int turn, int index) {
		this.turn = turn;
		this.index = index;
	}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(turn);
		out.writeInt(index);
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		this.turn = in.readInt();
		this.index = in.readInt();
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new PassTurn();
	}

	@Override
	public int getBufferCapacity() {
		return 8;
	}
}
