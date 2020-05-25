package com.souchy.randd.moonstone.commons.packets.s2c;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

/**
 * Server sends this to the clients whenever : a client passes turn (in response to PassTurn packet), or when the timer expires
 * 
 * @author Blank
 * @date 9 mai 2020
 */
@ID(id = 11003)
public class NextTurn implements BBMessage {

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
		return null;
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}
	
}
