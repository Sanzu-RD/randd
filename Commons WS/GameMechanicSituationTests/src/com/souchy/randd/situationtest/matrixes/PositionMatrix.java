package com.souchy.randd.situationtest.matrixes;

import com.souchy.randd.situationtest.interfaces.ICell;
import com.souchy.randd.situationtest.math.Point2D;
import com.souchy.randd.situationtest.math.Point3D;

/**
 * Use this for position so we determine occupied cells with a matrix so we can have big fat mobs taking 2x2 or 3x3 cell blocks etc
 * 
 * @author Souchy
 *
 */
public class PositionMatrix extends Matrix {
	
	
	public PositionMatrix(int[][] matrix) {
		super(matrix);
	}


	private Point2D matrixPos;
	
	private Point3D absolutePos;
	
	
	public Point3D getPos() {
		return absolutePos;
	}
	
	public void setPos(Point3D pos) {
		this.absolutePos = pos;
	}
	
	
	public void getCells() {
		
	}
	
	public boolean contains(ICell cell) {
		
		return false;
	}
	
}
