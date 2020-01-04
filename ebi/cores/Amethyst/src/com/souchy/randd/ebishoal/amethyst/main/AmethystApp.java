package com.souchy.randd.ebishoal.amethyst.main;

import com.souchy.randd.commons.tealwaters.io.files.FilesManager;
import com.souchy.randd.commons.tealwaters.logging.Log;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AmethystApp extends Application {
	
	public static Stage stage;
	public static Scene loginScene;
	public static Scene mainScene;
	
	@Override
	public void init() throws Exception {
		super.init();
		Amethyst.app = this;
		//if(Amethyst.module != null) FXMLLoader.setDefaultClassLoader(Amethyst.module.getClassLoader());
		FXMLLoader.setDefaultClassLoader(Amethyst.class.getClassLoader());
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		AmethystApp.stage = stage;
		
		loadScenes();
		
		loginScene.getRoot().setEffect(new DropShadow());
		loginScene.setFill(Color.TRANSPARENT);

		//stage.setScene(loginScene);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setTitle("Hidden Piranha");
		stage.getIcons().clear();
		stage.getIcons().add(new Image("file:/G:/Assets/pack/fantasy%20bundle/tcgcardspack/Tex_krakken.PNG"));
		stage.show();
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
		stage.close();
	}

	public void loadScenes() {
		boolean isLoggedIn = stage.getScene() != null && stage.getScene() == mainScene;
		loginScene = loadScene("login.fxml");
		mainScene = loadScene("main.fxml");
		if(isLoggedIn) stage.setScene(mainScene);
		else stage.setScene(loginScene);
	}
	
	public Scene loadScene(String name) {
		try {
			String devPath = "ux/amethyst/scenes/";
			String deployPath = "res/" + devPath;
			var url = FilesManager.getResource(devPath + name);
			if(url == null) url = FilesManager.getResource(deployPath + name);
			//Log.info("["+name+"] scene url : " + url);
			Parent parent = FXMLLoader.load(url);
			return new Scene(parent);
		} catch (Exception e) {
			Log.error("Amethyst unable to load the scene ["+name+"] : ", e);
			return null;
		}
	}
	
	
}
