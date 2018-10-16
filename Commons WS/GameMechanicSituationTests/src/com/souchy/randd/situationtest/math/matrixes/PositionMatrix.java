package com.souchy.randd.situationtest.math.matrixes;

import java.util.List;
import java.util.function.Consumer;

import com.souchy.randd.jade.api.ICell;
import com.souchy.randd.situationtest.math.Point2D;
import com.souchy.randd.situationtest.math.Point3D;
import com.souchy.randd.situationtest.models.org.FightContext;

/**
 * Use this for position so we determine occupied cells with a matrix so we can have big fat mobs taking 2x2 or 3x3 cell blocks etc
 * 
 * @author Souchy
 *
 */
public class PositionMatrix extends Matrix {

	/**
	 * Position du point d'origine dans la matrice
	 */
	private Point2D matrixPos;
	/**
	 * Position du point d'origine sur le board
	 */
	private Point3D absolutePos;
	
	
	public PositionMatrix(int[][] matrix) {
		super(matrix);
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				if(matrix[i][j] == Matrix.Flags.SOURCE) {
					matrixPos = new Point2D(i, j);
					break;
				}
			}
		}
	}
	
	public Point2D getMatrixPos() {
		return matrixPos;
	}
	
	public Point3D getAbsolutePos() {
		return absolutePos;
	}
	
	public void setPos(Point3D pos) {
		this.absolutePos = pos;
	}
	
	
	public List<ICell> getCells() {
		FightContext context = null;
		context.board.getCells(this);
		return null;
	}
	
	public boolean contains(ICell cell) {
		
		return false;
	}
	

	public void foreach(Consumer<ICell> boardCellAcceptor) {
		getCells().forEach(boardCellAcceptor);
	}
	
	
}
