package com.souchy.randd.commons.deathebi.msg;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

@ID(id = 1103)
public class SendSaltNull implements BBMessage {

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
		return new SendSaltNull();
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}
	
}
