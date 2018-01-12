package com.souchy.randd.modules.monitor.main;

import java.util.concurrent.Executors;

import com.souchy.randd.commons.tealwaters.io.files.FilesManager;

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
	
	
	
}
