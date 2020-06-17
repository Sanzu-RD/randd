package com.souchy.randd.commons.coral.in;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

/**
 * To get out of a queue / cancel queue
 * 
 * @author Blank
 * @date 16 juin 2020
 */
@ID(id = 7002)
public class Dequeue implements BBMessage {

	@Override
	public ByteBuf serialize(ByteBuf out) {
		return out;
	}

	@Override
	public Dequeue deserialize(ByteBuf in) {
		return null;
	}

	@Override
	public Dequeue create() {
		return new Dequeue();
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}
	
}
