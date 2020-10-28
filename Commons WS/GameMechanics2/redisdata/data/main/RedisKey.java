package data.main;

public interface RedisKey {

	public String name();
	public RedisKey parent();
	
	
	public default Object get() {
		return null;
	}
	public default void set(Object o) {
		
	}
	
}
