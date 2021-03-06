package com.souchy.randd.situationtest.matrixes;

import java.util.List;
import java.util.function.BiConsumer;

import com.souchy.randd.situationtest.math.Point2D;
import com.souchy.randd.situationtest.models.org.ContextualObject;
import com.souchy.randd.situationtest.models.org.FightContext;

public class Matrix extends ContextualObject {

	/*public static enum Flags {
		Null(-1),
		;
		public final int val;
		private Flags(int i) {
			val = i;
		}
	}*/
	
	/**
	 * TODO remove those flags ?
	 */
	/*public static final class Flags {
		public static final int NULL = 		 	-1;
		public static final int EMPTY = 	 	0b0000000000;
		public static final int SOURCE = 	 	0b0000000001;
		public static final int TARGET_CELL = 	0b0000000010;
		public static final int EFFECT_1 = 	 	0b0000000100;
		public static final int EFFECT_2 = 	 	0b0000001000;
		public static final int EFFECT_3 = 	 	0b0000010000;
	}*/
	
	//private final FightContext context;
	
	private MatrixFlags[][] matrix;
	
	
	public Matrix(FightContext context, MatrixFlags[][] matrix) {
		super(context);
		this.matrix = matrix;
	}
	public Matrix(FightContext context, int width, int height) { //int size) {
		super(context);
		this.matrix = new MatrixFlags[width][height];
	}
	
	/**
	 * Créé une map matrix autour de l'effectmatrix pour la positioner sur la map et pouvoir la comparer avec d'autres matrices
	 * @param e - effectmatrix ou positionmatrix
	 * @param p - Point dans la map matrix sur lequel positioner le coin haut-gauche de l'effectmatrix
	 * @param width - Largeur de la map matrix à créer
	 * @param height - Hauteur de la map matrix à créer
	 * @return La map matrix contenant l'effect matrix � la bonne position
	 */
	/*public static Matrix toMapMatrix(Matrix e, Point2D p, int width, int height) {
		if(false == (width >= e.getWidth() + p.x && height >= e.getWidth() + p.y && p.x >= 0 && p.y >= 0)) {
			return null;
		}
		Matrix m = new Matrix(width, height);
		e.foreach((i, j) -> {
			m.set(i + p.x, j + p.y, e.get(i, j));
		});
		return m;
	}*/
	
	public int getWidth() {
		if(matrix == null) return 0;
		return matrix.length;
	}

	public int getHeight() {
		if(matrix == null) return 0;
		return matrix[0].length;
	}
	
	public Matrix copy() {
		MatrixFlags[][] copy = new MatrixFlags[getWidth()][getHeight()];
		foreach((i, j) -> copy[i][j] = matrix[i][j]);
		return new Matrix(context, copy);
	}
	
	public void foreach(BiConsumer<Integer, Integer> consume) {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[i].length; j++) {
				consume.accept(i, j);
			}
		}
	}
	
	public MatrixFlags get(Point2D p) {
		if(p == null) return MatrixFlags.PositionningFlags.NoFlag; //Flags.NULL;
		return get(p.x, p.y);
	}
	
	public MatrixFlags get(int i, int j) {
		if(i < 0) return MatrixFlags.PositionningFlags.NoFlag; //Flags.NULL;
		if(j < 0) return MatrixFlags.PositionningFlags.NoFlag; //Flags.NULL;
		if(i > getWidth()) return MatrixFlags.PositionningFlags.NoFlag; //Flags.NULL;
		if(j > getHeight()) return MatrixFlags.PositionningFlags.NoFlag; //Flags.NULL;
		return matrix[i][j];
	}
	
	public void set(Point2D p, MatrixFlags val) {
		if(p == null) return;
		set(p.x, p.y, val);
	}
	
	public void set(int i, int j, MatrixFlags val) {
		if(i < 0) return;
		if(j < 0) return;
		if(i > getWidth()) return;
		if(j > getHeight()) return;
		matrix[i][j] = val;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < getWidth(); i++) {
			for(int j = 0; j < getHeight(); j++) {
				str.append(matrix[i][j]);
				if (j != getHeight() - 1) str.append(" ");
			}
			if(i != getWidth() - 1) str.append("\n");
		}
		return str.toString();
	}
	
	
	public List<Integer> filter(Matrix m) {
		
		return null;
	}
	
	/**
	 * 
	 * 
	 * @param a - Target matrix containing the positions of players
	 * @param b - Effect matrix 
	 * @return 
	 */
	/*public static Matrix ahh(Matrix a, Matrix b) {
		if(a == null || b == null || a.getWidth() != b.getWidth() || a.getWidth() != b.getWidth()) {
			return null;
		}
		Matrix m = new Matrix(a.getWidth(), a.getHeight());
		a.foreach((i, j) -> {
			if(a.get(i, j).getID() == MatrixFlags.PositionningFlags.Source.getID() 
				&& b.get(i, j).getID() >= MatrixFlags.EffectFlags.Effect1.getID()) {
				m.set(i, j, b.get(i, j));
			}
		});
		return m;
	}*/
	
}
