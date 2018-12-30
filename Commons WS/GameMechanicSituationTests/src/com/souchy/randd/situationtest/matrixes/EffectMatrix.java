package com.souchy.randd.situationtest.matrixes;

import com.souchy.randd.situationtest.math.Point2D;
import com.souchy.randd.situationtest.models.org.FightContext;

public class EffectMatrix extends Matrix {
	

	public Point2D origin;
	
	public EffectMatrix(FightContext context, MatrixFlags[][] matrix) { //, Point2D startingPoint) {
		super(context, matrix);
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				if(matrix[i][j] == MatrixFlags.PositionningFlags.Source) { //.SOURCE) {
					origin = new Point2D(i, j);
					break;
				}
			}
		}
	}
	
	
}
