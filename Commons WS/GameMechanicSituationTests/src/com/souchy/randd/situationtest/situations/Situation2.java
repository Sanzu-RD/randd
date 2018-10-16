package com.souchy.randd.situationtest.situations;

import com.souchy.randd.situationtest.math.Point2D;
import com.souchy.randd.situationtest.math.matrixes.EffectMatrix;
import com.souchy.randd.situationtest.math.matrixes.Matrix;
import com.souchy.randd.situationtest.models.stage.Board;

import javafx.scene.control.Cell;

public class Situation2 {
	
	
	public void test() {
		Matrix map = new Matrix(new int[][]{
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,5,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
		});
		Board board = new Board();
		map.foreach((i,j) -> {
			int cellTypeID = map.get(i, j);
		//	Cell cell = new Cell(cellTypeID);
		//	board.map.put(i, j, cell);
		});
		
		
		EffectMatrix effect = new EffectMatrix(new int[][] {
			{0,0,0,0,0},
			{0,0,0,0,0},
			{0,0,0,0,0},
		});
		
		
		map.foreach((i, j) -> {
			
		});
		
		Situation1.apply(map, effect, new Point2D(5,5), (appliedMap, eff, local, global) -> {
			if(appliedMap.get(global) == eff.get(local)) {
				
			}
		});
		
	}

}
