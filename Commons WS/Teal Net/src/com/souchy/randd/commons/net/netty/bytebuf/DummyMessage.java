package com.souchy.randd.commons.net.netty.bytebuf;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

@ID(id = 1)
@SuppressWarnings("exports")
public final class DummyMessage implements BBMessage {

	@Override
	public ByteBuf serialize(ByteBuf out) {
		return null;
	}

	@Override
	public BBMessage deserialize(ByteBuf out) {
		return null;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return null;
	}

	@Override
	public int getBufferCapacity() {
		return 10;
	}

}
