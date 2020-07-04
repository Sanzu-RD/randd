package gamemechanics.systems;

import data.new1.ecs.Engine;
import data.new1.ecs.Family;
import gamemechanics.models.Cell;

public class CellSystem extends Family<Cell> { 

	public CellSystem(Engine engine) {
		super(engine, Cell.class);
	}
	
	@Override
	public void update(float delta) {
//		Log.info("diamond cell system update");
		foreach(c -> {
			
		});
	}
	
	
}
