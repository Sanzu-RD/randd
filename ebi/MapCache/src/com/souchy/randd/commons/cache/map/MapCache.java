package com.souchy.randd.commons.cache.map;

import com.badlogic.gdx.Files.FileType;
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
	
	private String rootPath;

	public MapCache() {
		this(false);
	}
	public MapCache(boolean internal) {
		if(!internal) this.rootPath = "";
		else setRoot("", FileType.Internal);
	}
	public MapCache(String rootPath) {
		if(rootPath.contains(":/")) setRoot(rootPath, FileType.Absolute);
		else setRoot(rootPath, FileType.Internal);
	}
	public MapCache(FileHandle rootPath) {
		setRoot(rootPath);
	}
	public MapCache(String rootPath, FileType type) {
		setRoot(rootPath, type);
	}
	
	public void setRoot(FileHandle rootPath) {
		this.rootPath = rootPath.file().getAbsolutePath();
	}
	public void setRoot(String rootPath, FileType type) {
		switch(type) {
			case Internal: 	setRoot(Gdx.files.internal(rootPath)); 	break;
			case Classpath: setRoot(Gdx.files.classpath(rootPath)); break;
			case Local:  	setRoot(Gdx.files.local(rootPath)); 	break;
			case External:  setRoot(Gdx.files.external(rootPath)); 	break;
			case Absolute:  this.rootPath = rootPath; 	break;
		}
	}
	
	public MapData get(FileHandle file) {
		if(file == null) return null;
		if(!file.exists()) return null;
		if(!file.name().endsWith(".map") && !file.name().endsWith(".json")) return null;
		
		MapData map = gson.fromJson(file.reader(), MapData.class);
		return map;
	}
	
	@Override
	public MapData get(String path) {
		System.out.println("root1 = " + rootPath);
		System.out.println("cache.get : " + rootPath + "/" + path);
		FileHandle file = Gdx.files.absolute(rootPath + "/" + path);
		return get(file);
	}
	
	
	@Override
	public void set(String path, MapData data) {
		System.out.println("cache.set : " + rootPath + "/" + path);
		FileHandle f = Gdx.files.absolute(rootPath + "/" + path);
		if(!path.endsWith(".map") && !path.endsWith(".json")) return;
		
		String json = gson.toJson(data);
		f.writeString(json, false);
	}

	public String getRoot() {
		return rootPath;
	}
	
}
