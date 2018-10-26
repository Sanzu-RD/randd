package com.souchy.randd.ebishoal.sapphire.cache;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.souchy.randd.commons.cache.impl.JSONCache;
import com.souchy.randd.situationtest.models.map.MapData;

/**
 * Do it do this or a HashCache of <string, map>
 * i guess this is fine
 * 
 * @author Souchy
 *
 */
public class MapCache extends JSONCache<MapData> {
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Override
	public MapData get(String path) {
		FileHandle file = Gdx.files.internal(path);
		if(file == null) return null;
		if(!file.exists()) return null;
		
		MapData map = gson.fromJson(file.reader(), MapData.class);
		return map;
	}
	
	
	@Override
	public void set(String k, MapData v) {
		super.set(k, v);
		
		String json = gson.toJson(v);
		FileHandle f = Gdx.files.absolute(k);
		f.writeString(json, false);
		System.out.println(json);
	}
	
}
