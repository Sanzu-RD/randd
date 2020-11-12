package com.souchy.randd.ebishoal.amethyst.main;

import java.io.IOException;
import java.net.URL;

import com.souchy.randd.commons.tealwaters.io.files.FilesManager;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.amethyst.ui.LoginScene;
import com.souchy.randd.ebishoal.amethyst.ui.MainScene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AmethystApp extends Application {
	
	public static Stage stage;
	public static Scene loginScene;
	public static LoginScene loginController;
	public static Scene mainScene;
	public static MainScene mainController;
	
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
			if(!name.endsWith(".fxml")) name += ".fxml";
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
	
	
	public FXMLLoader loader(String name) {
		if(!name.endsWith(".fxml")) name += ".fxml";
		String devPath = "ux/amethyst/components/";
		String deployPath = "res/" + devPath;
		var url = FilesManager.getResource(devPath + name);
		if(url == null) url = FilesManager.getResource(deployPath + name);
		//Log.info("["+name+"] scene url : " + url);

		FXMLLoader fxmlLoader = new FXMLLoader(url);
		return fxmlLoader;
	}
	
	public <T extends Parent> T loadComponent(String name) {
		try {
			if(!name.endsWith(".fxml")) name += ".fxml";
			String devPath = "ux/amethyst/components/";
			String deployPath = "res/" + devPath;
			var url = FilesManager.getResource(devPath + name);
			if(url == null) url = FilesManager.getResource(deployPath + name);
			//Log.info("["+name+"] scene url : " + url);

			FXMLLoader fxmlLoader = new FXMLLoader(url);
//			try {
//				fxmlLoader.setRoot(controller);
//			} catch (Exception e) {
//				Log.error("Amethyst unable to load the component ["+name+"] : " + e.getMessage());
//			}
//			try {
//				fxmlLoader.setController(controller);
//			} catch (Exception e) {
//				Log.error("Amethyst unable to load the component ["+name+"] : " + e.getMessage());
//			}
			try {
				return (T) fxmlLoader.load();
			} catch (IOException e) {
				Log.error("Amethyst unable to load the component ["+name+"] : ", e);
			}
			return null;
//			return controller;
		} catch (Exception e) {
			Log.error("Amethyst unable to load the component ["+name+"] : ", e);
			return null;
		}
	}
	/**
	 * Inject
	 */
	public <T extends Parent> T loadComponent(T controller, String name) {
		try {
			if(!name.endsWith(".fxml")) name += ".fxml";
			String devPath = "ux/amethyst/components/";
			String deployPath = "res/" + devPath;
			var url = FilesManager.getResource(devPath + name);
			if(url == null) url = FilesManager.getResource(deployPath + name);
			//Log.info("["+name+"] scene url : " + url);

			FXMLLoader fxmlLoader = new FXMLLoader(url);
			try {
				fxmlLoader.setRoot(controller);
			} catch (Exception e) {
				Log.error("Amethyst unable to load the component ["+name+"] : " + e.getMessage());
			}
			try {
				fxmlLoader.setController(controller);
			} catch (Exception e) {
				Log.error("Amethyst unable to load the component ["+name+"] : " + e.getMessage());
			}
			try {
				fxmlLoader.load();
			} catch (IOException e) {
				Log.error("Amethyst unable to load the component ["+name+"] : ", e);
//				throw new RuntimeException(exception);
			}
			return controller;
//			var obj = FXMLLoader.load(url);
//			Log.info("loaded component " + obj + ", " + obj.getClass());
//			T out = (T) obj;
//			return out;
		} catch (Exception e) {
			Log.error("Amethyst unable to load the component ["+name+"] : ", e);
			return null;
		}
	}


	
}
