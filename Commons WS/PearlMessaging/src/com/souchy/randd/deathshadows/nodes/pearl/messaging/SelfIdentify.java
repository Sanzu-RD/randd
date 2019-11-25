package com.souchy.randd.deathshadows.nodes.pearl.messaging;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

@ID(id = 1001)
@SuppressWarnings("exports")
public class SelfIdentify implements BBMessage {

	@Override
	public ByteBuf serialize(ByteBuf out) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new SelfIdentify();
	}

	@Override
	public int getBufferCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}

}
