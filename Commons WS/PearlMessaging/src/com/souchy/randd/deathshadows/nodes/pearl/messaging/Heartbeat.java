package com.souchy.randd.deathshadows.nodes.pearl.messaging;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

/**
 * Nodes send an heartbeat to Pearl every x seconds
 * 
 * @author Blank
 * @date 13 juin 2020
 */
@ID(id = 0006)
public class Heartbeat implements BBMessage {

	@Override
	public ByteBuf serialize(ByteBuf out) {
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new Heartbeat();
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}
	
}
