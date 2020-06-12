package com.souchy.randd.deathshadows.nodes.pearl.messaging;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;

import io.netty.buffer.ByteBuf;

@ID(id = 1001)
public class SelfIdentify implements BBMessage {
	
	public int nodeid;
	
	public SelfIdentify(int nodeid) {
		this.nodeid = nodeid;
	}

	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(nodeid);
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		nodeid = in.readInt();
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new SelfIdentify(0);
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}

}
