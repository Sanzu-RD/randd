package com.souchy.randd.commons.coral.in;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

/**
 * Answer a match found message
 * 
 * @author Blank
 * @date 25 déc. 2019
 */
@ID(id = 7002)
public class MatchFoundAnswer implements BBMessage {

	public boolean accepted = false;
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		return out.writeBoolean(accepted);
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		accepted = in.readBoolean();
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new MatchFoundAnswer();
	}

	@Override
	public int getBufferCapacity() {
		return 1;
	}
	
}
