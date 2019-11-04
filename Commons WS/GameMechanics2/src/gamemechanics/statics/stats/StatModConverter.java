package gamemechanics.statics.stats;

import gamemechanics.statics.stats.modifiers.Modifier;
import gamemechanics.statics.stats.properties.StatProperty;

/** 
 * rate = value 1 / value2 
 * every value1 from statmod1 = 1 value2 from statmod2
 * so u can say ex : 2 fire flat = 3 water flat
 * or 1 white = 0 white to nullify
 */
public class StatModConverter extends StatMod {
	
	public StatProperty prop2;
	public int mods2;
	public double value2;
	/** if this is full conversion or as extra */
	public boolean extra;
	public StatModConverter(boolean extra, double value1, StatProperty prop1, Modifier[] mods1, double value2, StatProperty prop2, Modifier[] mods2) {
		super(value1, prop1, mods1);
		this.extra = extra;
		this.prop2 = prop2;
		this.value2 = value2;
		this.mods2 = Modifier.compile(mods2);
	}
	
}