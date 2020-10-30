package com.souchy.randd.deathshadows.nodes.pearl.messaging;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

/**
 * 
 * 
 * @author Blank
 * @date 14 juin 2020
 */
@ID(id = 1006)
public class AskKillNode implements BBMessage {

	public long id;
	public AskKillNode() {
	}
	public AskKillNode(long id) {
		this.id = id;
	}
	
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeLong(id);
		return out;
	}

	@Override
	public AskKillNode deserialize(ByteBuf in) {
		id = in.readLong();
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new AskKillNode();
	}

	
}
