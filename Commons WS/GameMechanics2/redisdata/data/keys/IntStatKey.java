package data.keys;

import data.main.IntKey;
import data.main.RedisKey;

public class IntStatKey implements RedisKey {

	private String name;
	
	public IntStatKey(String name) {
		this.name = name;
	}
	
	@Override
	public String name() {
		return name;
	}

	@Override
	public RedisKey parent() {
		return null;
	}
	
	
	public IntKey base = new IntKey(this, "base");
	public IntKey inc = new IntKey(this, "inc");
	public IntKey incflat = new IntKey(this, "incflat");
	public IntKey more = new IntKey(this, "more");
	
	
}
