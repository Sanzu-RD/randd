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

	public PassTurn() {
		
	}
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		return null;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		return null;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new PassTurn();
	}

	@Override
	public int getBufferCapacity() {
		return 1;
	}
}
