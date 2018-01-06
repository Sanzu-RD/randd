package com.souchy.randd.modules.monitor.io;


import java.util.Properties;

import com.hiddenpiranha.commons.tealwaters.properties.Property;
import com.hiddenpiranha.commons.tealwaters.properties.PropertyConfig;
import com.hiddenpiranha.commons.tealwaters.properties.PropertyFactory;

public class PluginMonitorConfig implements PropertyConfig {


	private final Properties props = new Properties();
	private final String path = "res/config.properties";
	//private final PropertyFactory factory = PropertyFactory.createFactory(this::get, this::set);
	
	public Property lastDirectory; //= factory.create("lastDirectory");
	public Property windowTitle;

//	public PluginMonitorConfig() {
//		initFields();
//	}

	@Override
	public String getPath() {
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
