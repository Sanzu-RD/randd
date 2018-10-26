package com.souchy.randd.ebishoal.commons.lapis.discoverers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;
import com.souchy.randd.commons.tealwaters.commons.Discoverer;

public class MapDiscoverer extends LibGdxFileDiscoverer<FileHandler> {


	@Override
	public boolean identify(FileHandle toFilter) {
		return toFilter.extension().contentEquals(".g3dj"); //FilesManager.get().hasExtension(toFilter, ".g3dj");
	}

	@Override
	protected FileHandler output(FileHandle file) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
