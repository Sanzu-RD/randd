package com.souchy.randd.situationtest.math.matrixes;

import com.souchy.randd.situationtest.math.Point2D;

public class EffectMatrix extends Matrix {
	

	public Point2D insideSource;
	
	public EffectMatrix(int[][] matrix) { //, Point2D startingPoint) {
		super(matrix);
		//this.startingPoint = startingPoint;
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				if(matrix[i][j] == Matrix.Flags.SOURCE) {
					insideSource = new Point2D(i, j);
					break;
				}
			}
		}
	}
	
	
}
