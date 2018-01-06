package com.souchy.randd.modules.monitor.main;
	
import com.souchy.randd.modules.base.BaseModuleInformationLoader;
import com.souchy.randd.modules.base.BaseModuleLoader;
import com.souchy.randd.modules.base.BaseModuleManager;
import com.souchy.randd.modules.monitor.ui.Root;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

	public static Stage stage;
	public static Scene scene;
	public static Root root;
	
	
	public static BaseModuleManager manager = new BaseModuleManager(new BaseModuleInformationLoader(), new BaseModuleLoader());
	
	@Override
	public void start(Stage primaryStage) {
		try {
			root = new Root();
			scene = new Scene(root);
			stage = primaryStage;
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
	        stage.setTitle("Plugin Monitor");
	        stage.initStyle(StageStyle.DECORATED);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	
}
