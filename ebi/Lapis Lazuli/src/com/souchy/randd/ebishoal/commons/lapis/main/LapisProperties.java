package com.souchy.randd.ebishoal.commons.lapis.main;

import java.io.File;
import java.util.Properties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.souchy.randd.commons.tealwaters.properties.Property;
import com.souchy.randd.commons.tealwaters.properties.PropertyConfig;

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
	
	private LwjglApplicationConfiguration config;
	
	public LapisProperties(LwjglApplicationConfiguration config) {
		this.config = config;
		this.properties = new Properties();
		this.file = new File("lapis.properties"); 
	}
	
	@Override
	public void load() {
		PropertyConfig.super.load();
		
		config.width = width.get();
		config.height = height.get();
		config.title = title.get();
		config.foregroundFPS = fpsFocused.get();
		config.backgroundFPS = fpsBackground.get();
		config.samples = samples.get();
		config.vSyncEnabled = vsync.get();
		
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
