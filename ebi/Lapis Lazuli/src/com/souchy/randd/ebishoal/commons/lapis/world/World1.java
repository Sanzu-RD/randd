package com.souchy.randd.ebishoal.commons.lapis.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class World {
	
	// public Table<Integer, Integer, ModelInstance> models;
	/**
	 * 
	 */
	public final ModelCache cache;
	
	public final List<ModelInstance> toCacheModels;
	public final List<ModelInstance> tempModels;
	// private final List<Lambda> cacheMods; // = new Array<>(); // modification aux
	// tiles de la grid qui impliquent qu'on rebuild le cache
	
	private AtomicBoolean dirty = new AtomicBoolean(true);
	
	public World() {
		cache = new ModelCache();
		// pu besoin d'avoir des synchronized() blocks partout grâce à ça
		toCacheModels = Collections.synchronizedList(new ArrayList<>());
		tempModels = Collections.synchronizedList(new ArrayList<>());
		// cacheMods = Collections.synchronizedList(new ArrayList<>());
	}
	
	/*public void addCell() {
		
	}*/
	
	
	/**
	 * 
	 * @param inst
	 */
	public void addToCache(ModelInstance inst) {
		toCacheModels.add(inst);
		dirty.set(true);
	}
	
	public void removeFromCache(ModelInstance inst) {
		toCacheModels.remove(inst);
		dirty.set(true);
	}
	
	public void clearCache() {
		toCacheModels.clear();
		dirty.set(true);
	}
	

	public void addTemp(ModelInstance inst) {
		tempModels.add(inst);
	}
	public void removeTemp(ModelInstance inst) {
		tempModels.remove(inst);
	}
	
	public void clearTemp() {
		tempModels.clear();
	}
	
	
	/**
	 * 
	 * @param l
	 */
	/*
	 * public void mod(Lambda l) { synchronized(cacheMods){ cacheMods.add(l); } }
	 */
	
	/*
	 * public void flushGridMods(){ synchronized (cacheMods) { if(cacheMods.size >
	 * 0){ cacheMods.forEach(Lambda::call); // apply mods cacheMods.clear(); //
	 * clear the mods list buildCache(); // rebuild the cache } } }
	 */
	
	public void buildCache() {
		if(dirty.get()) {
			cache.begin();
			toCacheModels.forEach(cache::add);
			cache.end();
			dirty.set(false);
		}
	}
	
	/*public void render(ModelBatch batch, Environment env, Camera cam) {
		batch.begin(cam);
		// render the cached models
		batch.render(cache, env);
		// render the temp models
		tempModels.forEach(m -> batch.render(m, env));
		batch.end();
	}*/
	
	public void dispose() {
		cache.dispose();
	}

	
}
