package com.souchy.randd.situationtest.models.stage;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.situationtest.interfaces.ICell;
import com.souchy.randd.situationtest.interfaces.ICombatEntity;
import com.souchy.randd.situationtest.interfaces.IEntity;
import com.souchy.randd.situationtest.math.Point3D;
import com.souchy.randd.situationtest.models.org.FightContext;
import com.souchy.randd.situationtest.scripts.Status;

public class Cell implements ICell {

	// see ICell pour commentaire sur les bus sur chaque cell
	private final EventBus bus = new EventBus();
	
	private Point3D pos;
	
	/** TODO Cell.List<Status> , du coup on pourrait tourner les cell en entities ? */
	public List<Status> statuss;
	/** TODO Cell.List<Entity> ou character ?? pour les tours de persos portés genre et initialement les trap/glyphs, mais ptete quon les change en statuts eux .. */
	public List<IEntity> entities;
	
	
	public Cell(int x, int y, int z) {
		pos = new Point3D(x, y, z);
	}

	@Override
	public EventBus bus() {
		return bus;
	}

	@Override
	public boolean blocksLineOfSight() {
		return false;
	}

	@Override
	public boolean walkable() {
		return false;
	}

	@Override
	public Point3D getPos() {
		return pos;
	}

	/**
	 * TODO Might delete Cell.getEntities ?
	 * <p>
	 * Might have getCharacters() pour les persos portés style panda ? <br>
	 * Et utiliser les status pour les terrain effects du genre kunai, trap, glyph ? ... <br>
	 */
	@Override
	public List<IEntity> getEntities() {
		List<IEntity> entities = new ArrayList<>();
		for(IEntity e : context.entities) {
			if(e.getOccupiedCells().contains(this)) {
				entities.add(e);
			}
		}
		return entities;
	}

	@Override
	public Character getCharacter() {
		return null;
	}
	@Override
	public boolean hasCharacter() {
		return false;
	}
	
	
}
