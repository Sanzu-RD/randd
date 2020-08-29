package com.souchy.randd.moonstone.commons.packets.s2c;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

/**
 * Server sends this to the clients whenever : a client passes turn (in response to PassTurn packet), or when the timer expires
 * or when a client connects in the middle of a turn, so it can know the timer
 * 
 * @author Blank
 * @date 9 mai 2020
 */
@ID(id = 11003)
public class TurnStart implements BBMessage {
	
	public int turn, index, time;
	
	
	public TurnStart() {
		
	}
	public TurnStart(int turn, int index, int time) {
		this.turn = turn;
		this.index = index;
		this.time = time;
	}

	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(turn);
		out.writeInt(index);
		out.writeInt(time);
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		this.turn = in.readInt();
		this.index = in.readInt();
		this.time = in.readInt();
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new TurnStart();
	}

	@Override
	public int getBufferCapacity() {
		return 8;
	}
	
}
