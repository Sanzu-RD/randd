package com.souchy.randd.ebishoal.commons.lapis.discoverers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;
import com.souchy.randd.commons.tealwaters.commons.Discoverer;

public class ModelDiscoverer implements Discoverer<String, FileHandle, Model> {

	/**
	 * This is really not good to have here i think. Should be outside.
	 * Could just return filehandles from here then let someone else load them. As should be since this is only a Discoverer
	 */
	public AssetManager manager = new AssetManager();
	
	@Override
	public List<Model> explore(String path) {
		List<Model> models = new ArrayList<>();

		FileHandle directory = Gdx.files.internal(path);
		if(directory == null) return models;
		if(!directory.exists()) return models;
		if(!directory.isDirectory()) return models;

		loop(models, directory);
		
		return models;
	}
	

	private void loop(List<Model> models, FileHandle directory) {
	    for(FileHandle file : directory.list()){
	    	if(file.isDirectory()){
	    		loop(models, file);
	    	}
	    	else if(identify(file)){
				manager.load(file.path(), Model.class);
	        }
	    }
	}

	@Override
	public boolean identify(FileHandle toFilter) {
		return toFilter.extension().contentEquals(".g3dj"); //FilesManager.get().hasExtension(toFilter, ".g3dj");
	}
	
}
