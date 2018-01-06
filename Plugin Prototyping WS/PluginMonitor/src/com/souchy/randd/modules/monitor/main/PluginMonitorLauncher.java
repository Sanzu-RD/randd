package com.souchy.randd.modules.monitor.main;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.hiddenpiranha.commons.tealwaters.io.files.FilesManager;

import javafx.application.Application;

public class PluginMonitorLauncher {

	
	public static void main(String[] args) throws Exception {
		/*File root = new File("res/config.properties");
		System.out.println(root.getCanonicalPath() + " : main root path canonical");
		System.out.println(root.getPath() + " : main root path normal");
		System.out.println(root.getCanonicalPath() + " : main root path w/e");
		
		if(root.listFiles().length > 0) {
			List<String> files = Arrays.stream(root.listFiles()).map(f -> f.getPath()).collect(Collectors.toList());
			files.add("hey");
			System.out.println("[" + String.join(", ", files) + "] : list files");
		}*/
		
		FilesManager.init(PluginMonitorLauncher.class);
		
		Executors.newSingleThreadExecutor().execute(() -> Application.launch(App.class));
		//Executors.newSingleThreadExecutor().execute(() -> App.launch(args));
		
		System.out.println("hello");
		//System.exit(0);
	}
	
	
	public static URL getResource(String name) {
		return PluginMonitorLauncher.class.getResource(name);
	}
	
	public static InputStream getResourceStream(String name) {
		return PluginMonitorLauncher.class.getResourceAsStream(name);
	}
	
	
}
