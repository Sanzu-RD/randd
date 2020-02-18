package data.new1.spellstats.base;

import data.new1.ecs.Component;

public class IntStat implements Component {
	
	public final double base;
	public double baseflat;
	public double inc;
	public double incflat;
	public double more;
	public double fight;
	
	/** this overrides everything */
	public IntStat setter;
	
	
	public IntStat(double base) {
		this.base = base;
	}
	
	public int value() {
		return max() + (int) fight;
//		val += fight;
//		return (int) val;
	}
	
	public int max() {
		if(setter != null) return setter.value();
		var val = base;
		val += baseflat; 
		val *= (1 + inc / 100d);
		val += incflat;
		val *= (1 + more / 100d);
		return (int) val;
	}
	
	
}
