package com.souchy.randd.deathshadow.core.smoothrivers;

import com.souchy.randd.annotationprocessor.ID;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.commons.Deserializer;
import com.souchy.randd.deathshadow.core.DeathShadowCore;

import io.netty.buffer.ByteBuf;

@ID(id = 1001)
public class SelfIdentify implements BBMessage {
	
	public int nodeid = 0;
	public Class<? extends DeathShadowCore> clazz = null;
	
	public SelfIdentify() {}
	public SelfIdentify(int nodeid, Class<? extends DeathShadowCore> clazz) {
		this.nodeid = nodeid;
		this.clazz = clazz;
	}
	public SelfIdentify(DeathShadowCore core) {
		this.nodeid = core.port;
		this.clazz = core.getClass();
	}

	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(nodeid);
	    writeString(out, clazz.getCanonicalName());
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		nodeid = in.readInt();
		try {
			clazz = (Class<? extends DeathShadowCore>) Class.forName(readString(in));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public Deserializer<ByteBuf, BBMessage> create() {
		return new SelfIdentify();
	}

	@Override
	public int getBufferCapacity() {
		return 0;
	}
	
	@Override
	public String toString() {
		return String.join(", ", String.valueOf(nodeid), clazz.getCanonicalName());
	}

}
