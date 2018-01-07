package com.souchy.randd.modules.monitor.io;


import java.io.File;
import java.util.Properties;

import com.randd.commons.tealwaters.properties.Property;
import com.randd.commons.tealwaters.properties.PropertyConfig;

public class PluginMonitorConfig implements PropertyConfig {


	private final Properties props = new Properties();
	private final File path = new File("res/config.properties");
	//private final PropertyFactory factory = PropertyFactory.createFactory(this::get, this::set);
	
	public Property lastDirectory; //= factory.create("lastDirectory");
	public Property windowTitle;

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

	
	public void asdf() {
		lastDirectory.set("");
	}

	
}
