package com.souchy.randd.ebishoal.commons.lapis.world;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.souchy.randd.commons.tealwaters.commons.Disposable;

public class World implements Disposable {
	
	// static terrain
	public ModelCache cache;
	// dynamic instances (cursor, creatures, terrain effects like glyphs and traps, highlighting effects ..)
	public List<ModelInstance> instances;
	
	protected Vector3 center = Vector3.Zero;
	
	public World() {
		cache = new ModelCache();
		instances = new ArrayList<>();
	}
	
	
	/**
	 * This is WRONG and INVALID
	 * (only checks translation transform + we use greedy meshing so it doesnt get the correct values)
	 */
	/*public BoundingBox getBoundingBox() {
    	Vector3 min = new Vector3();
    	Vector3 max = new Vector3();
		Vector3 v = new Vector3();
		Consumer<ModelInstance> consumer = inst -> {
    		inst.transform.getTranslation(v);
			if(v.x > max.x) max.x = v.x;
			if(v.x < min.x) min.x = v.x;
			if(v.y > max.y) max.y = v.y;
			if(v.y < min.y) min.y = v.y;
			if(v.z > max.z) max.z = v.z;
			if(v.z < min.z) min.z = v.z;
    	};
		//toCacheModels.forEach(consumer);
		//tempModels.forEach(consumer);
		instances.forEach(consumer);
    	return new BoundingBox(min, max);
	}*/

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		instances.clear();
		cache.dispose();
	}

	public Vector3 getCenter() {
		return center;
	}
	
}
