package data.new1.spellstats.base;

public class ObjectStat<T> {
	public T base;
	public T replacement;
	
	public T value() {
		if(replacement != null) return replacement;
		return base;
	}
	
}
