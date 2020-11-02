package com.souchy.randd.tools.rainbow.main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.souchy.randd.commons.tealwaters.io.files.FilesManager;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.rainbow.ui.Layout;
import com.souchy.randd.tools.rainbow.ui.LoginController;
import com.souchy.randd.tools.rainbow.ui.NodeDetails;
import com.souchy.randd.tools.rainbow.ui.NodeTable;
import com.souchy.randd.tools.rainbow.ui.PacketEditor;

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
	public static LoginController loginController;
	public static Scene mainScene;
	public static Layout mainController; // MainController mainController
	
	@Override
	public void start(Stage stage) throws Exception {
		Rainbow.app = this;
		
		//String jfxPath = "F:/Users/Souchy/Desktop/Robyn/Git/r and d/ebi/PiranhaPlants/res/ui/jfx/";
		
//		String devPath = "jfx/ui/rainbow/";
//		String deployPath = "res/" + devPath;
//		var loginUrl = FilesManager.getResource(deployPath + "rainbow-login.fxml");
//		if(loginUrl == null) loginUrl = FilesManager.getResource(devPath + "rainbow-login.fxml");
//		var mainUrl = FilesManager.getResource(deployPath + "rainbow.fxml");
//		if(mainUrl == null) mainUrl = FilesManager.getResource(devPath + "rainbow.fxml");
		
//		var cssUrl = new File("res/ux/amethyst/css/commons.css").toURI().toURL();
//		var loginUrl = new File("res/ux/rainbow/rainbow-login.fxml").toURI().toURL();
//		var mainUrl = new File("res/ux/rainbow/rainbow2.fxml").toURI().toURL();
		var background = new File("res/textures/Tex_krakken.PNG");
//		Log.info("loginurl " + loginUrl);
//		Log.info("mainUrl " + mainUrl);
		
//		Parent loginRoot = FXMLLoader.load(loginUrl); 
//		loginScene = new Scene(loginRoot);
//		loginScene.getStylesheets().add(cssUrl.toString());

//		Parent mainRoot = FXMLLoader.load(mainUrl);
//		mainScene = new Scene(mainRoot);
//		mainScene.getStylesheets().add(cssUrl.toString());

		
		preloadComponents();
		mainScene = new Scene(getLayout());
		mainScene.getStylesheets().add(new File("res/ux/rainbow/style.css").toURI().toURL().toString());
		

		Log.info("RainbowApp loaded scenes");

		//loginScene.getRoot().setStyle("-fx-padding: 20 20 20 20;"); // region.setPadding(new Insets(20,20,20,20));
//		loginScene.getRoot().setEffect(new DropShadow());
//		loginScene.setFill(Color.TRANSPARENT);
		
		RainbowApp.stage = stage;
		stage.setScene(mainScene);
//		stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Rainbow Piranha");
        stage.getIcons().clear();
        stage.getIcons().add(new Image(background.toURI().toURL().toString())); //"file:/G:/Assets/pack/fantasy%20bundle/tcgcardspack/Tex_krakken.PNG"));
		stage.show();
	}
	
	
	private static void preloadComponents() {
		try {
			components.put("nodetable", FXMLLoader.load(new File("res/ux/rainbow/nodetable.fxml").toURI().toURL())); // new NodeTable()); //
//			components.put("nodedetails", FXMLLoader.load(new File("res/ux/rainbow/nodedetails.fxml").toURI().toURL()));
			components.put("console", FXMLLoader.load(new File("res/ux/rainbow/console.fxml").toURI().toURL()));
			components.put("packeteditor", FXMLLoader.load(new File("res/ux/rainbow/packeteditor.fxml").toURI().toURL())); // new PacketEditor()); //
			
			components.put("layout", FXMLLoader.load(new File("res/ux/rainbow/layout.fxml").toURI().toURL())); // new Layout()); //
		} catch (Exception e) {
			Log.error("", e);
		}
	}
	
	private static Map<String, Parent> components = new HashMap<>();
	public static Parent getLayout() {
		return components.get("layout");
	}
	public static Parent getNodeTable() {
		return components.get("nodetable");
	}
//	public static Parent getNodeDetails() {
//		return components.get("nodedetails");
//	}
	public static Parent getConsole() {
		return components.get("console");
	}
	public static Parent getPacketEditor() {
		return components.get("packeteditor");
	}
	
}
