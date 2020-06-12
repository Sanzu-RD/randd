package com.souchy.randd.moonstone.commons.packets.c2s;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

@ID(id = 10004)
public class GetUpdate implements BBMessage {

	@Override
	public ByteBuf serialize(ByteBuf out) {
		return out;
	}

	@Override
	public GetUpdate deserialize(ByteBuf in) {
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new GetUpdate();
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}
	
}
