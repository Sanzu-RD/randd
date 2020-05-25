package data.new1.spellstats.imp;

import gamemechanics.statics.stats.properties.spells.TargetingProperty;

/**
 * Determines what kind of targets are affectable/targetable based on TargetingProperty's
 * 
 * @author Blank
 * @date 26 févr. 2020
 */
public class TargetConditionStat {
	
	/** By default : can target everything and need a line of sight */
	public int base = all();

	/** this overrides everything */
	public TargetConditionStat setter;
	
	public int value() {
		if(setter != null) return setter.value();
		return base;
	}

	/**
	 * @return an int that activates all bits
	 */
	public int all() {
		int bits = 0;
		for(var bit : TargetingProperty.values())
			bits |= (int) Math.pow(bit.ordinal(), 2);
		return bits;
	}
	
	public void add(TargetingProperty p) {
		this.base |= (int) Math.pow(p.ordinal(), 2);
	}
}