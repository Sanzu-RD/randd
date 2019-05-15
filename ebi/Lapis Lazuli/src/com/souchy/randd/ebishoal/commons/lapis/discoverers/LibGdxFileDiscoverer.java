package com.souchy.randd.ebishoal.commons.lapis.discoverers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.souchy.randd.commons.tealwaters.commons.Discoverer;

public abstract class LibGdxFileDiscoverer<O> implements Discoverer<String, FileHandle, O> {

	
	@Override
	public List<O> explore(String path) {
		List<O> files = new ArrayList<>();

		FileHandle directory = Gdx.files.internal(path);
		if(directory == null) return files;
		if(!directory.exists()) return files;
		//if(!directory.isDirectory()) return files;

		var f = directory.file();
		var s = f.getAbsolutePath();
		FileHandle absDir = Gdx.files.absolute(f.getAbsolutePath());

		loop(files, absDir);
		
		return files;
	}
	
	private void loop(List<O> files, FileHandle directory) {
		var a = directory.list();
	    for(FileHandle file : directory.list()){
	    	if(file.isDirectory()){
	    		loop(files, file);
	    	}
	    	else if(identify(file)){
	    		files.add(output(file));
	    	//	files.add(file);
	        }
	    }
	}
	
	protected abstract O output(FileHandle file);
	
	
	/*@Override
	public boolean identify(FileHandle toFilter) {
		return toFilter.extension().contentEquals(".g3dj"); //FilesManager.get().hasExtension(toFilter, ".g3dj");
	}*/
}
