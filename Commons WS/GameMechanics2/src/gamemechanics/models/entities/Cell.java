package gamemechanics.models.entities;

import java.util.List;

import gamemechanics.common.generic.Vector2;

public class Cell extends Entity {
	
	// main creature on the cell (ex si elle porte ou mange un autre creature (ex tahm kench/pandawa))
	public List<Creature> creatures;
	
	
	
	public Cell(int x, int y) {
		this.pos = new Vector2(x, y);
	}
	
	
	public boolean hasCreature() {
		return !getCreatures().isEmpty();
	}
	public List<Creature> getCreatures() {
//		for(Creature c : fight.timeline) {
//			if(c.getCell() == this) return c;
//		}
//		return null;
		return creatures;
	}
	
	
	
	
}