package com.souchy.randd.moonstone.commons.packets;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;

import io.netty.buffer.ByteBuf;

@ID(id = 99001)
public class CreateFight implements BBMessage {

	@Override
	public ByteBuf serialize(ByteBuf out) {
		return out;
	}

	@Override
	public CreateFight deserialize(ByteBuf in) {
		return this;
	}

	@Override
	public CreateFight create() {
		var packet = new CreateFight();
		return packet;
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}
	
}
