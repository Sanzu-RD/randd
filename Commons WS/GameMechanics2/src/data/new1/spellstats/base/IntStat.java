package data.new1.spellstats.base;

import data.new1.ecs.Component;

public class IntStat implements Component {
	
	/** flat value */
	//public final double base;
	public double baseflat;
	/** % increase */
	public double inc;
	public double incflat;
	/** % more */
	public double more;
	/**
	 * fight modifier separates the current from the max/total value for resources
	 */
	public double fight;
	
	/** this overrides everything */
	public IntStat setter;
	
	public IntStat(double base) {
		//this.base = base;
		this.baseflat = base;
	}
	
	public int value() {
		return max() + (int) fight;
		// val += fight;
		// return (int) val;
	}
	
	public int max() {
		if(setter != null) return setter.value();
		var val = 0; //base;
		val += baseflat;
		val *= (1d + inc / 100d);
		val += incflat;
		val *= (1d + more / 100d);
		return (int) val;
	}
	
	public IntStat copy() {
		var s = new IntStat(baseflat); //base);
		s.baseflat = baseflat;
		s.inc = inc;
		s.incflat = incflat;
		s.more = more;
		s.fight = fight;
		return s;
	}
	
}
