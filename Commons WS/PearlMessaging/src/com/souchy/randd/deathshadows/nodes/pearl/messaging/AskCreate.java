package com.souchy.randd.deathshadows.nodes.pearl.messaging;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

@ID(id = 1003)
public class AskCreate implements BBMessage {

	public String coreName;
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		writeString(out, coreName);
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		coreName = readString(in);
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new AskCreate();
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}

}
