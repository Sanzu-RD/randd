package com.souchy.randd.tools.rainbow.main;

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

public class RainbowApp extends Application {

	public static Stage stage;
	public static Scene loginScene;
	public static Scene mainScene;
	
	@Override
	public void start(Stage stage) throws Exception {
		Rainbow.app = this;
		
		//String jfxPath = "F:/Users/Souchy/Desktop/Robyn/Git/r and d/ebi/PiranhaPlants/res/ui/jfx/";
		
		String devPath = "jfx/ui/rainbow/";
		String deployPath = "res/" + devPath;
		var loginUrl = FilesManager.getResource(deployPath + "rainbow-login.fxml");
		if(loginUrl == null) loginUrl = FilesManager.getResource(devPath + "rainbow-login.fxml");
		var mainUrl = FilesManager.getResource(deployPath + "rainbow.fxml");
		if(mainUrl == null) mainUrl = FilesManager.getResource(devPath + "rainbow.fxml");
		
		Parent loginRoot = FXMLLoader.load(loginUrl); 
		loginScene = new Scene(loginRoot);
		
		Parent mainRoot = FXMLLoader.load(mainUrl);
		mainScene = new Scene(mainRoot);

		Log.info("RainbowApp loaded scenes");

		//loginScene.getRoot().setStyle("-fx-padding: 20 20 20 20;"); // region.setPadding(new Insets(20,20,20,20));
		loginScene.getRoot().setEffect(new DropShadow());
		loginScene.setFill(Color.TRANSPARENT);
		
		RainbowApp.stage = stage;
		stage.setScene(loginScene);
		stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Rainbow Piranha");
        stage.getIcons().clear();
        stage.getIcons().add(new Image("file:/G:/Assets/pack/fantasy%20bundle/tcgcardspack/Tex_krakken.PNG"));
		stage.show();
	}
	
}
