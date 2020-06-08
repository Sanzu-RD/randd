package data.new1.ecs;

public abstract class System {

	public System() {
		Engine.add(this);
		Engine.bus.register(this);
	}
	
	public abstract void update(float delta);
	
}
