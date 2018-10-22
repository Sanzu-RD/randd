package com.souchy.randd.situationtest.math.matrixes;

import com.souchy.randd.situationtest.models.map.Cell;

public class ConditionMatrix extends Matrix {

	public ConditionMatrix(int[][] matrix) {
		super(matrix);
	}
	
	
	public boolean check(Cell c) {
		return false;
	}
	
	
}
