package com.souchy.randd.mockingbird.lapismock.worlds;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;

public class World5 {

	public ModelCache cache;
	
	public World5() {
		// load cube model
		String path = "cube.g3dj";
		AssetManager assets = new AssetManager();
		assets.load(path, Model.class);
		assets.finishLoading();
		Model cubeModel = assets.get(path); 
		
		// create small platform
		float size = 15;
		int scl = 1;
		cache = new ModelCache();
		cache.begin();
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				ModelInstance inst = new ModelInstance(cubeModel);
				inst.transform.translate(i, j, 0).scale(scl, scl, scl);
				if((i + j) % 2 == 0)
					inst.materials.get(0).set(ColorAttribute.createDiffuse(Color.DARK_GRAY));
				else 
					inst.materials.get(0).set(ColorAttribute.createDiffuse(Color.GRAY));
        		inst.materials.get(0).set(new BlendingAttribute(1f)); 
				cache.add(inst);
			}
		}
		cache.end();
	}
	
}
