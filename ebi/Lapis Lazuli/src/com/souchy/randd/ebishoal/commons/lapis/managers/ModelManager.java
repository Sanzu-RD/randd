package com.souchy.randd.ebishoal.commons.lapis.managers;

import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.Array;

public class ModelManager {

	private final AssetManager assets;
	private final HashMap<String, String> aliases = new HashMap<>(); //LinkedHashMap<>(); 
	
	public ModelManager(AssetManager assets) {
		this.assets = assets;
	}
	
	
	public void load(String internal) {
		load(Gdx.files.internal(internal));
	}
	
	public void load(List<FileHandle> files) {
		files.forEach(f -> load(f));
	}

	public void load(FileHandle file) {
		assets.load(file.path(), Model.class);

		String filename = file.file().getName();
		String name = filename.substring(0, filename.indexOf(".")); 
		
		aliases.put(name, file.path());
	}

	public void loadSync(List<FileHandle> files) {
		files.forEach(f -> load(f));
		finishLoading();
	}
	public Model loadSync(FileHandle file) {
		load(file);
		finishLoading();
		return get(file.path());
	}
	public Model loadSync(String internal) {
		return loadSync(Gdx.files.internal(internal));
	}
	
	
	public Model get(String name) {
		if(name == null) return null;
		var alias = aliases.get(name);
		if(alias == null) return null;
		return assets.get(alias);
	}
	
	public void finishLoading() {
		assets.finishLoading();
	}
	
	public Array<String> getAssetNames() {
		return assets.getAssetNames();
	}
	
}
