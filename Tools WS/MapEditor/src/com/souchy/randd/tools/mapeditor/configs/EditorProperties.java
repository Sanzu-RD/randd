package com.souchy.randd.tools.mapeditor.configs;

import java.io.File;
import java.util.Properties;

import com.badlogic.gdx.Gdx;
import com.souchy.randd.commons.tealwaters.properties.Property;
import com.souchy.randd.commons.tealwaters.properties.PropertyConfig;

public class EditorProperties implements PropertyConfig {

	private Properties properties;
	private File file;
	
	
	public Property<Float> ambiantBrightness;
	public Property<Float> directionalBrightness;
	public Property<Boolean> showGrid;
	public Property<Integer> directionalLightCount;
	
	
	public EditorProperties() {
		file = Gdx.files.internal("data/editor.properties").file();
		properties = new Properties();
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
