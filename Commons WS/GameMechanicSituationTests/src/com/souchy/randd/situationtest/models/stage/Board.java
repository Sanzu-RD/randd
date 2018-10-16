package com.souchy.randd.situationtest.models.stage;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.souchy.randd.jade.api.IBoard;
import com.souchy.randd.jade.api.ICell;
import com.souchy.randd.jade.api.IEntity;
import com.souchy.randd.situationtest.math.Point2D;
import com.souchy.randd.situationtest.math.matrixes.EffectMatrix;
import com.souchy.randd.situationtest.math.matrixes.MatrixFlags;
import com.souchy.randd.situationtest.math.matrixes.PositionMatrix;
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
	
	public List<ICell> getPosition(IEntity e){
		List<ICell> pos = new ArrayList<>();
		map.values().forEach(c -> {
			if(c.getCharacter() == e) //.getEntities(context).contains(e))
				pos.add(c);
		});
		return pos;
	}

	public ICell getCell(int x, int y) {
		if(map.contains(x, y)) {
			return map.get(x, y);
		}
		return null;
	}
	public ICell getCell(Point2D p) {
		return getCell(p.x, p.y);
	}
	
	/**
	 * autrefois List<IEntity>, ptete qu'on devrait avoir les deux ou quoi ?
	 * <p>
	 * on obtient les entit�s � partir des celles anyway : List<IEntity> = List<Cell>.map(c -> c.getEntity();
	 * 
	 * @param targetCell
	 * @param aoe
	 * @param targetFlags
	 * @return
	 */
	public List<ICell> getTargets(ICell targetCell, Orientation ori, EffectMatrix aoe){ // , int targetFlags){ // -> au lieu de targetFlags, on utilise un predicate dans Effect.applyAoe(target, pred<cell>)
		// TODO board.getTargets en se basant sur les situations 1-2

		List<ICell> cells = new ArrayList<>();
		if(targetCell == null) return cells;
		aoe.foreach((i, j) -> {
			if(aoe.get(i, j) >= MatrixFlags.EffectFlags.Effect1.getID()) { // si *any* effect flag
				int x = targetCell.getPos().x + i;
				int y = targetCell.getPos().y + j;
				if(map.contains(x, y)) {
					cells.add(map.get(x, y));
				}
			}
		});
		return cells;
	}
	
	/*public List<IEntity> getTargets(Matrix m, MatrixFlags f) {
		List<IEntity> entities = new ArrayList<>();
		m.foreach((i, j) -> {
			if(m.get(i, j) == f.getID()) {
				entities.add(map.get(j, j).getCharacter());
			}
		});
		return entities;
	}*/
		
	/**
	 * 
	 * @param matrix
	 * @return
	 */
	public List<ICell> getCells(PositionMatrix matrix){
		List<ICell> cells = new ArrayList<>();
		matrix.foreach((i, j) -> {
			int x = matrix.getAbsolutePos().x - matrix.getMatrixPos().x + i;
			int y = matrix.getAbsolutePos().y - matrix.getMatrixPos().x + j;
			if(map.contains(x, y)) {
				cells.add(map.get(x, y));
			}
		});
		return cells;
	}
	
	
	public Orientation getDefaultSpellOrientation(Character source, Cell targetCell) {
		
		return null;
	}
	
}
