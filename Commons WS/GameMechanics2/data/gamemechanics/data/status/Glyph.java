package gamemechanics.data.status;

import java.util.List;

import data.new1.timed.Status;
import gamemechanics.events.OnEnterCell.OnEnterCellHandler;
import gamemechanics.events.OnLeaveCell.OnLeaveCellHandler;
import gamemechanics.events.OnTurnEnd.OnTurnEndHandler;
import gamemechanics.events.OnTurnStart.OnTurnStartHandler;
import gamemechanics.models.entities.Cell;

/**
 * 
 * Une instance de glyphe suffit puis on la donne en statut à toutes les cells
 *
 */
public abstract class Glyph extends Status implements OnTurnStartHandler, OnTurnEndHandler, OnEnterCellHandler, OnLeaveCellHandler {
	
	/** toutes les cells affectées par le status de glyphe */
	public List<Cell> cells;
	
	public Glyph() {
		// duration ?
		// Entity source = the glyph caster
		// Entity target = the cell which was targeted to place the glyph
	}

	@Override
	public void fuse(Status s) {
		refreshSet(s.duration);
	}

	
	//  ---------------------------------------------------   Méthode 2 : applique effects si la creature est dans une zone qui contient ce status
	/*
	@Override
	public void onLeaveCell(OnLeaveCell e) {
		
	}

	@Override
	public void onEnterCell(OnEnterCell e) {
		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onTurnStart(OnTurnStart e) {
		if(e.target.getCell().getStatus().has(this.getClass())) // 2 manière de checker la cell. Soit si la cell a le status, soit si la cell est dans la liste de cells
			for(Effect effect : effects) 
				if(effect instanceof CreatureEffect) effect.apply(this.source, e.target);
				else if(effect instanceof CellEffect) effect.apply(this.source, e.target.getCell());
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onTurnEnd(OnTurnEnd e) {
		if(e.target.getCell().getStatus().has(this.getClass()))
			for(Effect effect : effects) 
				if(effect instanceof CreatureEffect) effect.apply(this.source, e.target);
				else if(effect instanceof CellEffect) effect.apply(this.source, e.target.getCell());
	}
	*/

	/*
	@Override
	public void onStatusExpire(OnStatusExpire e) {
		
	}
	*/


	//  ---------------------------------------------------   Méthode 1 : ajout un statut sur les créatures qui rentrent et sortent de la zone
	/*
	 * 
	@Override
	public void onEnterCell(OnEnterCell e) {
		if(cells.contains(e.cell))
			e.creature.getStatus().add(this);
	}
	
	@Override
	public void onLeaveCell(OnLeaveCell e) {
		if(cells.contains(e.cell))
			e.creature.getStatus().remove(this.getClass());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onTurnEnd(OnTurnEnd e) {
		if(cells.contains(e.target.getCell()))
			for(Effect effect : effects) 
				if(effect instanceof CreatureEffect) effect.apply(this.source, e.target);
				else if(effect instanceof CellEffect) effect.apply(this.source, e.target.getCell());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onTurnStart(OnTurnStart e) {
		if(cells.contains(e.target.getCell()))
			for(Effect effect : effects) 
				if(effect instanceof CreatureEffect) effect.apply(this.source, e.target);
				else if(effect instanceof CellEffect) effect.apply(this.source, e.target.getCell());
	}
	*/
	
	
}
