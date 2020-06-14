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

	public int id;
	public AskKillNode() {
	}
	public AskKillNode(int id) {
		this.id = id;
	}
	
	
	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(id);
		return out;
	}

	@Override
	public AskKillNode deserialize(ByteBuf in) {
		id = in.readInt();
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new AskKillNode();
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}
	
}
