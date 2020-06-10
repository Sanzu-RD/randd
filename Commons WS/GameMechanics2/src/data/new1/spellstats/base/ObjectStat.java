package data.new1.spellstats.base;

import gamemechanics.common.Aoe;

public class ObjectStat<T> {
	public T base;
	public T setter;
	
	public ObjectStat(T t) {
		this.base = t;
	}
	
	public T value() {
		if(setter != null) return setter;
		return base;
	}

	
}
