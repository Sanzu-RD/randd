package com.souchy.randd.mockingbird.lapismock.cleaner;

import java.util.List;

import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.souchy.randd.commons.diamond.ext.MapData;
import com.souchy.randd.commons.tealwaters.commons.Disposable;


public class World implements Disposable {

    public static ModelCache cache;
	public List<ModelInstance> instances;
	
    public static void createWorld(MapData data) {
    	
    }


	public BoundingBox getBoundingBox() {
    	Vector3 min = new Vector3();
    	Vector3 max = new Vector3();
		Vector3 v = new Vector3();
    	instances.forEach(inst -> {
    		inst.transform.getTranslation(v);
    		if(v.x > max.x) {
    			max.x = v.x;
    		}
    		if(v.x < min.x) {
    			min.x = v.x;
    		}
    		if(v.y > max.y) {
    			max.y = v.y;
    		}
    		if(v.y < min.y) {
    			min.y = v.y;
    		}
    		if(v.z > max.z) {
    			max.z = v.z;
    		}
    		if(v.z < min.z) {
    			min.z = v.z;
    		}
    	});
    	return new BoundingBox(min, max);
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
    
}
