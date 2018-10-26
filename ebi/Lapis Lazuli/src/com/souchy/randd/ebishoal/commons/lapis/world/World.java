package com.souchy.randd.ebishoal.commons.lapis.world;

import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.google.common.collect.Table;

public class World {
	
	public Table<Integer, Integer, ModelInstance> models;
	public ModelCache cache;
	
	private boolean dirty;
	
	public World() {
		cache = new ModelCache();
	}
	
	
	
	
}
