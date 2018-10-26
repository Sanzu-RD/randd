package com.souchy.randd.ebishoal.commons.lapis.discoverers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;

public class ModelDiscoverer extends LibGdxFileDiscoverer<FileHandle> {

	/**
	 * This is really not good to have here i think. Should be outside.
	 * Could just return filehandles from here then let someone else load them. As should be since this is only a Discoverer
	 */
	//public AssetManager manager = new AssetManager();

	@Override
	public boolean identify(FileHandle toFilter) {
		return toFilter.extension().contentEquals(".g3dj"); //FilesManager.get().hasExtension(toFilter, ".g3dj");
	}


	@Override
	protected FileHandle output(FileHandle file) {
		//manager.load(file.path(), Model.class);
		//Model m = manager.get(file.name());
		return file; //m;
	}
	
}
