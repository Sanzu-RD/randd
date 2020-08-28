package console;


import com.souchy.randd.commons.tealwaters.io.files.FilesManager;
import com.souchy.randd.commons.tealwaters.logging.Log;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConsoleApp extends Application {

	public Stage stage;
	public Scene scene;
	
	
	public static void main() {
		Application.launch(ConsoleApp.class);
	}
	
	
	@Override
	public void init() throws Exception {
		super.init();
		FXMLLoader.setDefaultClassLoader(ConsoleApp.class.getClassLoader());
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		scene = loadScene("console.fxml");
		stage.setScene(scene);
		stage.setWidth(1600);
		stage.setHeight(900);
		
		//stage.setScene(loginScene);
//		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setTitle("Hidden Piranha Console");
		stage.getIcons().clear();
		stage.getIcons().add(new Image("file:/G:/Assets/pack/fantasy%20bundle/tcgcardspack/Tex_krakken.PNG"));
		stage.show();
		
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		stage.close();
	}


	public Scene loadScene(String name) {
		try {
			String devPath = ""; //"ux/amethyst/scenes/";
			String deployPath = ""; //"res/" + devPath;
			var url = FilesManager.getResource(devPath + name);
			if(url == null) url = FilesManager.getResource(deployPath + name);
			//Log.info("["+name+"] scene url : " + url);
			Parent parent = FXMLLoader.load(url);
			return new Scene(parent);
		} catch (Exception e) {
			Log.error("Console unable to load the scene ["+name+"] : ", e);
			return null;
		}
	}
	
	
	
}
