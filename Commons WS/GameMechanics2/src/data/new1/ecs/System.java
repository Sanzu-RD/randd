package data.new1.ecs;

/**
 * 
 * 
 * @author Blank
 * @date 10 juin 2020
 */
public abstract class System {

	// secret hack
	protected Engine engine;
	
	public System(Engine engine) {
		this.engine = engine;
		engine.add(this);
		engine.bus.register(this);
	}

	public void dispose() {
		engine.remove(this);
	}
	
	public abstract void update(float delta);
	
}
