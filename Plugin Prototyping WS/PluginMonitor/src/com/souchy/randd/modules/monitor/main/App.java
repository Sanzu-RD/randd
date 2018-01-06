package com.souchy.randd.modules.monitor.main;

import com.hiddenpiranha.commons.tealwaters.io.files.FilesManager;
import com.souchy.randd.modules.base.BaseModuleInformationLoader;
import com.souchy.randd.modules.base.BaseModuleLoader;
import com.souchy.randd.modules.base.BaseModuleManager;
import com.souchy.randd.modules.monitor.io.PluginMonitorConfig;
import com.souchy.randd.modules.monitor.ui.Root;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

	public static Stage stage;
	public static Scene scene;
	public static Root root;
	
	public static PluginMonitorConfig config = new PluginMonitorConfig();
	public static BaseModuleManager manager = new BaseModuleManager(new BaseModuleInformationLoader(), new BaseModuleLoader());
	
	@Override
	public void start(Stage primaryStage) {
		try {
			config.load();
			stage = primaryStage;
			root = new Root();
			scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
	        stage.setTitle("Plugin Monitor");
	        stage.initStyle(StageStyle.DECORATED);
	        stage.getIcons().clear();
	        stage.getIcons().add(new Image("file:Iconsmind-Outline-Monitor-Analytics.ico"));
	        //stage.getIcons().add(new Image(FilesManager.getResourceAsStream("Iconsmind-Outline-Monitor-Analytics.ico")));
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	


}
