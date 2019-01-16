package com.souchy.randd.situationtest.models.map;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.souchy.randd.jade.api.ICell;
import com.souchy.randd.jade.api.AEntity;
import com.souchy.randd.situationtest.math.Point3D;
import com.souchy.randd.situationtest.models.entities.Character;
import com.souchy.randd.situationtest.scripts.Status;

public class Cell implements ICell {

	// see ICell pour commentaire sur les bus sur chaque cell
	private final EventBus bus = new EventBus();
	
	private Point3D pos;
	private boolean walkable;
	private boolean los;
	
	/** TODO Cell.List<Status> , du coup on pourrait tourner les cell en entities ? */
	public List<Status> statuss;
	/** TODO Cell.List<Entity> ou character ?? pour les tours de persos port�s genre et initialement les trap/glyphs, mais ptete quon les change en statuts eux .. */
	public List<AEntity> entities;
	public Character character;
	
	public Cell(int x, int y, int z) {
		pos = new Point3D(x, y, z);
	}

	public Cell(int x, int y, int z, boolean walkable, boolean los) {
		this(x, y, z);
		this.walkable = walkable;
		this.los = los;
	}
	
	@Override
	public EventBus bus() {
		return bus;
	}

	@Override
	public boolean walkable() {
		return walkable;
	}

	@Override
	public boolean blocksLineOfSight() {
		return los;
	}

	@Override
	public Point3D getPos() {
		return pos;
	}

	/**
	 * TODO Might delete Cell.getEntities ?
	 * <p>
	 * Might have getCharacters() pour les persos port�s style panda ? <br>
	 * Et utiliser les status pour les terrain effects du genre kunai, trap, glyph ? ... <br>
	 */
	@Override
	public List<AEntity> getEntities() {
		List<AEntity> entities = new ArrayList<>();
		/*for(IEntity e : context.entities) {	// comment� pour pouvoir compiler sans erreur en attendant
			if(e.getOccupiedCells().contains(this)) {
				entities.add(e);
			}
		}*/
		return entities;
	}

	@Override
	public Character getCharacter() {
		return character;
	}
	@Override
	public boolean hasCharacter() {
		return character != null;
	}
	public void testSetCharacter(Character c) {
		this.character = c;
	}
	
	
}
