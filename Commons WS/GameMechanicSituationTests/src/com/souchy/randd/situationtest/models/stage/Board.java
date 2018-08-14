package com.souchy.randd.situationtest.models.stage;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.souchy.randd.situationtest.interfaces.IBoard;
import com.souchy.randd.situationtest.interfaces.ICell;
import com.souchy.randd.situationtest.interfaces.IEntity;
import com.souchy.randd.situationtest.matrixes.EffectMatrix;
import com.souchy.randd.situationtest.matrixes.PositionMatrix;
import com.souchy.randd.situationtest.properties.types.Orientation;

public class Board implements IBoard {
	
	/** Cell[][] ? */
	public Table<Integer, Integer, ICell> map;
	
	public Board() {
		map = HashBasedTable.create();
	}

	@Override
	public Table<Integer, Integer, ICell> getCells() {
		return map;
	}
	
	public List<Cell> getPosition(IEntity e){
		List<Cell> pos = new ArrayList<>();
		map.values().forEach(c -> {
			if(c.getEntities(context).contains(e))
				pos.add(c);
		});
		return pos;
	}
	
	/**
	 * autrefois List<IEntity>, ptete qu'on devrait avoir les deux ou quoi ?
	 * <p>
	 * on obtient les entités à partir des celles anyway : List<IEntity> = List<Cell>.map(c -> c.getEntity();
	 * 
	 * @param targetCell
	 * @param aoe
	 * @param targetFlags
	 * @return
	 */
	public List<Cell> getTargets(Cell targetCell, Orientation ori, EffectMatrix aoe){ // , int targetFlags){ // -> au lieu de targetFlags, on utilise un predicate dans Effect.applyAoe(target, pred<cell>)
		
		// TODO board.getTargets en se basant sur les situations 1-2
		
		return null;
	}
	
	/**
	 * 
	 * @param matrix
	 * @return
	 */
	public List<Cell> getCells(PositionMatrix matrix){
		List<Cell> cells = new ArrayList<>();
		
		return null;
	}
	
	
	public Orientation getDefaultSpellOrientation(Character source, Cell targetCell) {
		
		return null;
	}
	
}
