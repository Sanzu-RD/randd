package com.souchy.randd.ebishoal.commons.lapis.managers;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;

public class ModelManager {

	private final AssetManager assets;
	private final HashMap<String, String> aliases = new HashMap<>(); //LinkedHashMap<>(); 
	
	public ModelManager(AssetManager assets) {
		this.assets = assets;
	}
	
	public void load(String internal) {
		load(Gdx.files.internal(internal));
	}

	public void load(FileHandle file) {
		assets.load(file.path(), Model.class);

		String filename = file.file().getName();
		String name = filename.substring(0, filename.indexOf(".")); // filename.substring(filename.lastIndexOf("/")+1, filename.indexOf("."));
		
		aliases.put(name, file.path());
	}

	public Model loadSync(FileHandle file) {
		load(file);
		finishLoading();
		return get(file.path());
	}
	
	
	public Model get(String name) {
		return assets.get(aliases.get(name));
	}
	
	public void finishLoading() {
		assets.finishLoading();
	}
	
}
