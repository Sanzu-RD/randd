package data.new1.spellstats.base;

public class BoolStat {
	
	public boolean base;
	public boolean replaced, replacement;
	
	public boolean value() {
		return replaced ? replacement : base;
	}
	
}
