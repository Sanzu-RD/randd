package com.souchy.randd.commons.diamond.models.stats.special;

import com.souchy.randd.commons.diamond.statics.filters.Height;
import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;

import io.netty.buffer.ByteBuf;

public class HeightStat implements BBSerializer, BBDeserializer {
	
	/**
	 * By default : all (for effects)
	 */
	public int base = Height.all();
	
	/**
	 * Fight overrides the base
	 */
	public int fight = base;
	

	public HeightStat() {
		
	}
	
	public HeightStat(int base) {
		this.base = base;
		this.fight = base;
	}
	
	public int value() {
		return fight; //base & fight;
	}
	
	public void add(Height h) {
		this.fight |= h.bit;
	}
	
	public void remove(Height h) {
		if(has(h))
			this.fight -= h.bit;
	}
	
	public void set(Height h) {
		this.fight = h.bit;
	}
	
	public void set(HeightStat s) {
		this.base = s.base;
		this.fight = s.fight;
	}
	
	public boolean has(Height h) {
		return (value() & h.bit) != 0;
	}
	
	public void reset() {
		this.fight = base;
	}
	

	@Override
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(base);
		out.writeInt(fight);
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		base = in.readInt();
		fight = in.readInt();
		return null;
	}
	
	public HeightStat copy() {
		var copy = new HeightStat();
		copy.base = base;
		copy.fight = fight;
		return copy;
	}

	public Height single() {
		return Height.fromBit(value());
	}
	
}
