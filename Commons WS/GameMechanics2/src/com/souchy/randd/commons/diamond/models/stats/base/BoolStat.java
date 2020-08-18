package com.souchy.randd.commons.diamond.models.stats.base;

import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;

import io.netty.buffer.ByteBuf;

public class BoolStat implements BBSerializer, BBDeserializer {
	
	public boolean base;
	public boolean replaced, replacement;
	
	
	public BoolStat(boolean base) {
		this.base = base;
	}
	
	public boolean value() {
		return replaced ? replacement : base;
	}

	public void replace(boolean should) {
		replaced = true;
		replacement = should;
	}
	public void reset() {
		replaced = false;
	}

	public BoolStat copy() {
		var s = new BoolStat(base);
		s.replaced = replaced;
		s.replacement = replacement;
		return s;
	}

	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeBoolean(base);
		out.writeBoolean(replaced);
		out.writeBoolean(replacement);
		return null;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		this.base = in.readBoolean();
		this.replaced = in.readBoolean();
		this.replacement = in.readBoolean();
		return null;
	}
	
}
