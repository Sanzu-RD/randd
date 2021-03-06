package com.souchy.randd.modules.monitor.io;


import java.io.File;
import java.util.Properties;

import com.souchy.randd.commons.tealwaters.properties.Property;
import com.souchy.randd.commons.tealwaters.properties.PropertyConfig;

public class PluginMonitorConfig implements PropertyConfig {


	private final Properties props = new Properties();
	private final File path = new File("res/config.properties");
	//private final PropertyFactory factory = PropertyFactory.createFactory(this::get, this::set);
	
	public Property<String> lastDirectory; //= factory.create("lastDirectory");
	public Property<String> windowTitle;

//	public PluginMonitorConfig() {
//		initFields();
//	}

	@Override
	public File getFile() {
		return path;
	}
	@Override
	public Properties getProperties() {
		return props;
	}

	
}
