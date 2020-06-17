package com.souchy.randd.commons.coral.out;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

/**
 * If a match is denied either at the answer screen or if someone leaves the match screen (dodge)
 * 
 * @author Blank
 * @date 25 déc. 2019
 */
@ID(id = 7013)
public class MatchLost implements BBMessage {

	@Override
	public ByteBuf serialize(ByteBuf out) {
		return out;
	}

	@Override
	public MatchLost deserialize(ByteBuf in) {
		return this;
	}

	@Override
	public MatchLost create() {
		return new MatchLost();
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}
	
}
