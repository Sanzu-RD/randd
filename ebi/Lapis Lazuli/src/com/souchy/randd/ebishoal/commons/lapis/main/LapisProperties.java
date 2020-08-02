package com.souchy.randd.ebishoal.commons.lapis.main;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.commons.tealwaters.properties.Property;
import com.souchy.randd.commons.tealwaters.properties.PropertyConfig;

/**
 * Pour {@link com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration}
 * 
 * @author Blank
 */
public class LapisProperties implements PropertyConfig {

	protected final Properties properties;
	protected final File file;
	
	
	public Property<Integer> width;
	public Property<Integer> height;
	public Property<String> title;
	public Property<Integer> fpsFocused;
	public Property<Integer> fpsBackground;
	public Property<Integer> samples;
	public Property<Boolean> vsync;
	public Property<String> root;

	/** Preferences directory on the desktop. Default is ".prefs/". */
	public Property<String> preferencesDirectory; // = ".prefs/";
	/** Preferences file type on the desktop. Default is FileType.External */
	public Property<Files.FileType> preferencesFileType; // = FileType.External;
	
	
	private LwjglApplicationConfiguration config;
	
	public LapisProperties(LwjglApplicationConfiguration config) {
		this.config = config;
		this.properties = new Properties();
		this.file = new File("lapis.properties"); 
	}
	
	@Override
	public void load() {
		PropertyConfig.super.load();
		Log.info("root = " + root.get());
		
//		for(var field : config.getClass().getFields()) {
//			var val = properties.get(field.getName());
//			try {
//				field.set(config, field.getType().cast(val));
//			} catch (IllegalArgumentException | IllegalAccessException e1) {
//				e1.printStackTrace();
//			}
//		}
		
		config.width = width.get();
		config.height = height.get();
		config.title = title.get();
		config.foregroundFPS = fpsFocused.get();
		config.backgroundFPS = fpsBackground.get();
		config.samples = samples.get();
		config.vSyncEnabled = vsync.get();
		config.preferencesDirectory = preferencesDirectory.get();
		config.preferencesFileType = preferencesFileType.get();
		
		var file = new LwjglFileHandle(new File(preferencesDirectory.get(), "sapphire"), preferencesFileType.get());
		Log.info("pref file : " + file.file().getAbsolutePath());
		
		
		
		width.addListener((e) -> config.width = (int) e.getNewValue());
		height.addListener((e) -> config.height = (int) e.getNewValue());
		title.addListener((e) -> {
			 config.title = (String) e.getNewValue();
			 Gdx.graphics.setTitle(config.title);
			 System.out.println("setting config title : " + e.getNewValue());
		});
		fpsFocused.addListener((e) -> config.foregroundFPS = (int) e.getNewValue());
		fpsBackground.addListener((e) -> config.backgroundFPS = (int) e.getNewValue());
		samples.addListener((e) -> config.samples = (int) e.getNewValue());
		vsync.addListener((e) -> {
			config.vSyncEnabled = (boolean) e.getNewValue();
			Gdx.graphics.setVSync(config.vSyncEnabled);
		});
		width.addListener((e) -> config.preferencesDirectory = (String) e.getNewValue());
		width.addListener((e) -> config.preferencesFileType = (FileType) e.getNewValue());
	}
	
	@Override
	public File getFile() {
		return file;
	}

	@Override
	public Properties getProperties() {
		return properties;
	}

	
}
