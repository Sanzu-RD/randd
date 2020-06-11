package com.souchy.randd.commons.coral.out;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.jade.mm.GameQueue;

import io.netty.buffer.ByteBuf;

@ID(id = 7011)
public class EnqueueAck implements BBMessage {
	
	public GameQueue queue;
	public boolean alreadyQueued;
	public boolean queued;
	
	public EnqueueAck() {}
	public EnqueueAck(GameQueue queue, boolean alreadyQueued, boolean queued) {
		this.queue = queue;
		this.alreadyQueued = alreadyQueued;
		this.queued = queued;
	}

	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeByte(queue.ordinal());
		out.writeBoolean(alreadyQueued);
		out.writeBoolean(queued);
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		queue = GameQueue.values()[in.readByte()];
		alreadyQueued = in.readBoolean();
		queued = in.readBoolean();
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new EnqueueAck();
	}

	@Override
	public int getBufferCapacity() {
		return 3;
	}
	
}
