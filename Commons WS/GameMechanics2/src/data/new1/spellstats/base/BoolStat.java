package data.new1.spellstats.base;

public class BoolStat {
	
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
	
}
