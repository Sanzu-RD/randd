package com.souchy.randd.tools.mapeditor.configs;

import java.io.File;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.badlogic.gdx.Gdx;
import com.souchy.randd.commons.tealwaters.properties.*;

public class EditorProperties implements PropertyConfig {

	private Properties properties;
	private File file;
	
	
	public Property<Float> ambiantBrightness;
	
	
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
