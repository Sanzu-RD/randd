package gamemechanics.events.new1;

public interface Handler {
	
	public static enum HandlerType {
		Interceptor,
		Modifier,
		Reactor;
	}
	
	
	
	public HandlerType type();
	
}
