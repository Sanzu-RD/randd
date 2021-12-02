package com.souchy.randd.commons.reddiamond.stats;

public class IntStat {

	/** flat value */
	public double baseflat;
	/** % increase */
	public double inc;
	/** increase flat after %inc */
	public double incflat;
	/** % more */
	public double more;
	/**
	 * fight modifier separates the current from the max/total value for resources
	 */
	//public double fight;
	
	
	public IntStat add(IntStat i) {
		
		return this;
	}
	
	public IntStat copy() {
		var c = new IntStat();
		c.baseflat = baseflat;
		c.inc = inc;
		c.incflat = incflat;
		c.more = more;
		//c.fight = fight;
		return c;
	}
	
}
