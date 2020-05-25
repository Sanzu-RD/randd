package data.new1.timed;

import java.util.List;

import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;
import gamemechanics.models.entities.Entity.EntityRef;

/**
 * 
 * 
 * @author Blank
 * @date mars 2020
 */
public abstract class TerrainEffect extends Status {

	public List<Cell> aoe;

	public TerrainEffect(EntityRef source, EntityRef target) {
		super(source, target);
	}
	
	public abstract String texture9Patch();
	public abstract String textureCenter();
	
}
