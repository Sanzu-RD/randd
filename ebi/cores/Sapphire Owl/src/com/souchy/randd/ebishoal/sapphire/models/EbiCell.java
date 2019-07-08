package com.souchy.randd.ebishoal.sapphire.models;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.google.inject.Inject;

public class EbiCell extends models.entities.Cell {

	public ModelInstance model;
	
//	@Inject
//	public EbiCell(int x, int y, int z, boolean walkable, boolean los, ModelInstance model) {
//		super(x, y, z, walkable, los);
//		
//		this.model = model;
//	}
	
	@Inject
	public EbiCell(int x, int y, ModelInstance model) {
		super(x, y);
		this.model = model;
	}
	
	
	/**
	 * 
	 * @param dz - delta z, distance de translation en z
	 */
	public void translateZ(int dz) {
		model.transform.translate(0, 0, dz);
	}
	
	
}
