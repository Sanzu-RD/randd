package gamemechanics.common;

@FunctionalInterface
public interface EventHandler<T extends FightEvent> {
	
	
	public void onEvent(T e);
	
}
