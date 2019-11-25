package com.souchy.randd.deathshadows.nodes.pearl.messaging;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

@SuppressWarnings("exports")
@ID(id = 1002)
public class AskAvailable implements BBMessage {

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBufferCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}


}
