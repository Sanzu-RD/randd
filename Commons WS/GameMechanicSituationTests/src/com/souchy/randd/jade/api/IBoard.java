package com.souchy.randd.jade.api;

import java.util.List;

import com.google.common.collect.Table;
import com.souchy.randd.situationtest.matrixes.EffectMatrix;
import com.souchy.randd.situationtest.properties.types.Orientation;

public interface IBoard {

	public Table<Integer, Integer, ICell> getCells();
	
	public ICell getCell(int x, int y);

	public List<ICell> getTargets(ICell targetCell, Orientation ori, EffectMatrix aoe);
	
}
