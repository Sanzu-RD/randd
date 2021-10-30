package com.souchy.randd.commons.tealwaters.io.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.ZonedDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.io.files.JsonHelpers.Exclude;
import com.souchy.randd.commons.tealwaters.io.files.JsonHelpers.InstantAdapter;
import com.souchy.randd.commons.tealwaters.io.files.JsonHelpers.ZonedDateTimeAdapter;
import com.souchy.randd.commons.tealwaters.logging.Log;

public class JsonConfig {

	private static final String extension = ".conf";
	public static final Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeHierarchyAdapter(Instant.class, new InstantAdapter())
			.registerTypeHierarchyAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
			.setExclusionStrategies(JsonHelpers.exclusionStrategy)
			.create();
	
	
	// -------------------------------------------
	
	@Exclude
	protected Path rememberPath;
	
	public static <T> T readAny(Class<T> c, String path) {
		try {
			var p = Environment.fromRoot(path);
			if(Files.exists(p)) return gson.fromJson(Files.readString(p), c);
			else {
				Log.error("JsonConfig file does not exist : " + path);
				return null;
			}
		} catch (Exception e) {
			Log.error("JsonConfig cannot read file : " + path + ".\n", e);
			return null;
		}
	}
	
	private static <T extends JsonConfig> String name(Class<T> c) {
		return c.getSimpleName().toLowerCase().replace("config", "").replace("conf", "") + extension;
	}
	
	private static <T extends JsonConfig> T read(Class<T> c, Path path) {
		try {
			T config = null;
			if(Files.exists(path)) {
				//Log.info("path : " + path);
				//Log.info("content : " + Files.readString(path));
				config = gson.fromJson(Files.readString(path), c);
			} else {
				config = c.getConstructor()/* .getDeclaredConstructor() */.newInstance();
			}
			config.rememberPath = path;
			config.save();
			return config;
		} catch (Exception e) {
			//e.printStackTrace();
			Log.error("JsonConfig cannot read file : " + path + ".\n", e);
			return null;
		}
	}

	public static <T extends JsonConfig> T read(Class<T> c, String path) {
		//var url = FilesManager.getResource(path + name(c));
		var p = Environment.fromRoot(path + name(c));
		//Log.info("url = "  + url);
		//Log.info("p = "  + p);
		//Log.info("env : " + Environment.fromRoot(path + name(c)));
		try {
			return read(c, p); //Paths.get(url.toURI())); 
		} catch (Exception e) {
			//e.printStackTrace();
			Log.error("JsonConfig cannot read file : " + path + ".\n", e);
			return null;
		}
	}
	public static <T extends JsonConfig> T read(Class<T> c) {
		return read(c, "");
	}
	/**
	 * 
	 * @param <T> - Type of Config
	 * @param c - Config sub class
	 * @param filePaths - Can be either absolute or relative to current application directory. Will default to "./" if no given path is valid.
	 * @return T Config instance
	 */
	public static <T extends JsonConfig> T readExternal(Class<T> c, String... filePaths) {
		//Log.info("readExternal");
		String defaultPath = "./";
		String chosenPath = null;
		if(filePaths == null || filePaths.length == 0) filePaths = new String[] { defaultPath };
		for(var path : filePaths) 
			if(Environment.exists(path)) //new File(path).exists()) 
				chosenPath = path;
		if(chosenPath == null) chosenPath = defaultPath;
		try {
			//Log.info("readExternal chosenPath : " + chosenPath);
			var file = Environment.getFile(chosenPath + name(c)); //FilesManager.getFileOutside(chosenPath + name(c));
			//Log.info("readExternal file : " + file.getAbsolutePath());
			var config = read(c, file.toPath());
			//Log.info("readExternal config : " + file);
			return config;
		} catch(Exception e) {
			//Log.warning("readExternal error : ", e);
			Log.error("JsonConfig cannot read external file : " + chosenPath + ".\n", e);
			return null;
		}
	}
	
	public void save() {
		var content = gson.toJson(this);
		//var file = new File(rememberPath + name(this.getClass()));
		//Log.info("save " + this.rememberPath);
		try {
			if(rememberPath == null) 
				rememberPath = Environment.fromRoot("" + name(this.getClass()));
			Files.writeString(rememberPath/*file.toPath()*/, content);
		} catch (IOException e) {
			//e.printStackTrace();
			Log.error("JsonConfig cannot save : ", e);
		}
	}
	
}
