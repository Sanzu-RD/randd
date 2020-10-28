package data.keys;

import data.main.RedisKey;

public class CreatureKey implements RedisKey {

	@Override
	public String name() {
		return "creature";
	}

	@Override
	public RedisKey parent() {
		return null;
	}

	@Override
	public Object get() {
		return null; // redis
	}

}
