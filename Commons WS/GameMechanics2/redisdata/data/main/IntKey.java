package data.main;

public class IntKey implements RedisKey {
	
	private String name;
	private RedisKey parent;
	
	
	public IntKey(RedisKey parent, String name) {
		this.name = name;
		this.parent = parent;
		if(parent != null) this.name = parent.name() + name;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public RedisKey parent() {
		return parent;
	}


}
