package com.souchy.randd.situationtest.models.map;

import com.souchy.randd.situationtest.matrixes.MatrixFlags.CellPropsFlags;

public final class MapData {

	/**
	 * Matrix for each cell model
	 */
	public int[][] cells = new int[0][0];

	/**
	 * Matrix for each cell Z axis
	 */
	public int[][] elevation = new int[0][0];

	/**
	 * Matrix for each cell 
	 */
	public int[][] walkableLos = new int[0][0];
	

	public int getWidth() {
		if(getHeight() == 0) return 0;
		return cells[0].length;
	}
	
	public int getHeight() {
		return cells.length;
	}
	
	public int getModel(int x, int y) {
		return cells[y][x];
	}
	public int getElevation(int x, int y) {
		return elevation[y][x];
	}
	public boolean getWalkable(int x, int y) {
		return isWalkable(x, y);
	}
	public boolean getLos(int x, int y) {
		return isLos(x, y);
	}
	public boolean isWalkable(int x, int y) {
		return (walkableLos[y][x] & CellPropsFlags.Walkable.getID()) == CellPropsFlags.Walkable.getID();
	}
	public boolean isLos(int x, int y) {
		return (walkableLos[y][x] & CellPropsFlags.LineOfSight.getID()) == CellPropsFlags.LineOfSight.getID();
	}
	public void setModel(int x, int y, int model) {
		cells[y][x] = model;
	}
	
	public ModelData getModelData(int x, int y) {
		return models[cells[y][x]];
	}
	public MaterialData getMaterialData(int x, int y) {
		return materials[getModelData(x, y).material];
	}
	
	
	public ModelData[] models;
	public MaterialData[] materials;
	
	public static final class ModelData {
		public final int id;
		public final String model;
		public final int material;
		public ModelData(int id, String model, int material) {
			this.id = id;
			this.model = model;
			this.material = material;
		}
	}
	public static final class MaterialData {
		public final float[] ambient;
		public final float[] diffuse;
		public final float[] emissive;
		public final float opacity;
		public MaterialData(float[] ambient, float[] diffuse, float[] emissive, float opacity) {
			this.ambient = ambient;
			this.diffuse = diffuse;
			this.emissive = emissive;
			this.opacity = opacity;
		}
	}
	
	

	/*
	public static final class Elevations {
		public final int minZ;
		public final int maxZ;
		public final int depth;
		public Elevations(int minZ, int maxZ, int depth) {
			this.minZ = minZ;
			this.maxZ = maxZ;
			this.depth = depth;
		}
	}
	/**
	 * @return new Elevations(minZ, maxZ, maxZ - minZ)
	 * /
	public Elevations getElevations() {
		int minZ = 0;
		int maxZ = 0;
		for(int y = 0; y < getHeight(); y++) {
			for(int x = 0; x < getWidth(); x++) {
				int z = getElevation(x, y);
				if(z > maxZ) {
					maxZ = z;
				}
				if(z < minZ) {
					minZ = z;
				}
			}
		}
		return new Elevations(minZ, maxZ, maxZ - minZ);
	}*/

	public int getMaxElevation() {
		int maxZ = 0;
		for(int y = 0; y < getHeight(); y++) {
			for(int x = 0; x < getWidth(); x++) {
				int z = getElevation(x, y);
				if(z > maxZ) {
					maxZ = z;
				}
			}
		}
		return maxZ;
	}
	
}
