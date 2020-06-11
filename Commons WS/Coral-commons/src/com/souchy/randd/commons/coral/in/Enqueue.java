package com.souchy.randd.commons.coral.in;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.jade.mm.GameQueue;

import io.netty.buffer.ByteBuf;

@ID(id = 7001)
public class Enqueue implements BBMessage {

	public GameQueue queue;
	
	private Enqueue() { }
	public Enqueue(GameQueue queue) {
		this.queue = queue;
	}

	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeByte(queue.ordinal());
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		int ordinal = in.readByte();
		this.queue = GameQueue.values()[ordinal];
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new Enqueue();
	}

	@Override
	public int getBufferCapacity() {
		return 1; // jsute 1 byte
	}
	
}
