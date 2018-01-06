package com.hiddenpiranha.commons.tealwaters.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public interface PropertiesConfig {


	static Properties props = new Properties();
	
	//protected abstract Properties getProperties();
	

	default void set(String val){
		props.setProperty(this.toString(), val);
		//value = val;
	}
	default String get(){
		if(props.isEmpty()) load(props);
		return props.getProperty(this.toString());
		//return value;
	}
	
	public static void load(Properties props){
		try {
			
			props.load(PluginMonitorConfig.class.getClassLoader().getResourceAsStream("res/config.properties"));
			//props.entrySet().forEach(e -> 
			//	Config.valueOf((String) e.getKey()).set((String)e.getValue())
			//);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void save(Properties props) {
		try {
			props.store(new FileOutputStream("res/config.properties"), "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
