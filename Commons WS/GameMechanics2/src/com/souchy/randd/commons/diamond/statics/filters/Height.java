package com.souchy.randd.commons.diamond.statics.filters;

import com.souchy.randd.commons.diamond.models.stats.special.HeightStat;

/**
 * Height of an entity: underground, on the ground, or above ground.
 * An effect can be programmed to target any and all heights
 * 
 * @author Blank
 * @date 28 déc. 2020
 */
public enum Height {
	
	under,
	floor,
	above;
	
	public final int bit = 1 << ordinal();

	public HeightStat stat() {
		return new HeightStat(bit);
	}
	
	public static Height fromBit(int bit) {
		if(bit == floor.bit) return floor; else 
		if(bit == above.bit) return above; else 
		return under;
	}
	
	public static int all() {
		return under.bit | floor.bit | above.bit;
	}
	
}
