package com.souchy.randd.situationtest.matrixes;

import com.souchy.randd.situationtest.models.map.Cell;
import com.souchy.randd.situationtest.models.org.FightContext;

public class ConditionMatrix extends Matrix {

	public ConditionMatrix(FightContext context, MatrixFlags[][] matrix) {
		super(context, matrix);
	}
	
	
	public boolean check(Cell c) {
		return false;
	}
	
	
}
