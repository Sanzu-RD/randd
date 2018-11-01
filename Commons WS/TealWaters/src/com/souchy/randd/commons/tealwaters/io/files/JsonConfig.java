package com.souchy.randd.commons.tealwaters.io.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;


public interface JsonConfig {
	
	/**
	 * use new File("...") for general <br>
	 * use Gdx.files.Internal("...") for libgdx app 
	 * @return
	 */
	public File getFile();
	

	default void load() {
		try {
			InputStream in;
			// in = FilesManager.get().getResourceAsStream(getPath());s
			File f = getFile(); // new File(getPath());
			System.out.println("load : " + f.getAbsolutePath());
			
			Gson gson = new Gson();
			
			gson.fromJson(f.getAbsolutePath(), this.getClass());
			
			
			in = new FileInputStream(f);
			getProperties().load(in);

			initFields();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> propstrings = getProperties().entrySet().stream().map(e -> e.getKey() + " = " + e.getValue()).collect(Collectors.toList());
		System.out.println("load : " + "[" + String.join(", ", propstrings) + "]");
	}

	
	
}
