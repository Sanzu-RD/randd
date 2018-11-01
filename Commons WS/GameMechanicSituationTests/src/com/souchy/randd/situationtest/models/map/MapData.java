package com.souchy.randd.situationtest.models.map;

import com.souchy.randd.situationtest.matrixes.MatrixFlags.CellPropsFlags;

public class MapData {

	/**
	 * Matrix for each cell model
	 */
	public int[][] models = new int[0][0];

	/**
	 * Matrix for each cell Z axis
	 */
	public int[][] elevation = new int[0][0];

	/**
	 * Matrix for each cell 
	 */
	public int[][] walkableLos = new int[0][0];
	
	
	//public static final byte WALKABLE = 1;
	//public static final byte LOS = 2;

	public int getWidth() {
		return models.length;
	}
	
	public int getHeight() {
		if(models.length == 0) return 0;
		return models[0].length;
	}
	
	public int getModel(int x, int y) {
		return models[x][y];
	}
	public int getElevation(int x, int y) {
		return elevation[x][y];
	}
	public boolean getWalkable(int x, int y) {
		return isWalkable(x, y);
	}
	public boolean getLos(int x, int y) {
		return isLos(x, y);
	}
	public boolean isWalkable(int x, int y) {
		return (walkableLos[x][y] & CellPropsFlags.Walkable.getID()) == 1;
	}
	public boolean isLos(int x, int y) {
		return (walkableLos[x][y] & CellPropsFlags.LineOfSight.getID()) == 1;
	}
	public void setModel(int x, int y, int model) {
		models[x][y] = model;
	}

	
}
