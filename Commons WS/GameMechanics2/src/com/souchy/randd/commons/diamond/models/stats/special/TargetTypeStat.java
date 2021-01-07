package com.souchy.randd.commons.diamond.models.stats.special;

import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;

import io.netty.buffer.ByteBuf;

/**
 * Determines what kind of targets are affectable/targetable based on TargetingProperty's
 * 
 * @author Blank
 * @date 26 févr. 2020
 */
public class TargetTypeStat implements BBSerializer, BBDeserializer {
	
	/** By default : can target everything and need a line of sight */
	public int base = all();
	
	/** this overrides everything */
//	public TargetConditionStat setter;
	public int fight = all();
	
	public TargetTypeStat() {
		
	}
	public TargetTypeStat(int val) {
		this.base = fight = val;
	}
	public static TargetTypeStat from(int val) {
		return new TargetTypeStat(val);
	}
	
	public int value() {
//		if(setter != null) return setter.value();
		return base & fight;
	}

	/**
	 * @return an int that activates all bits
	 */
	public int all() {
		int bits = 0;
		for(var p : TargetType.values())
			bits |= p.bit();
		return bits;
	}
	/**
	 * @return an int that deactivates all bits
	 */
	public int none() {
		return 0;
	}

	public boolean accepts(TargetType p) {
		return (value() & p.bit()) != 0;
	}
	
	/**
	 * Activates a target type in fight
	 */
	public void addFight(TargetType p) {
		this.fight |= p.bit();
	}
	/**
	 * Deactivates a target type in fight
	 */
	public void removeFight(TargetType p) {
		int val = p.bit();
		if((this.fight & val) != 0) 
			this.fight -= val;
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
	
	public TargetTypeStat copy() {
		var copy = new TargetTypeStat();
		copy.base = base;
		copy.fight = fight;
		return copy;
	}
	
}