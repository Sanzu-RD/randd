package com.souchy.randd.ebishoal.amethyst.ui;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.amethyst.main.AmethystApp;
import com.souchy.randd.ebishoal.coraline.Coraline;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public abstract class Common {


    private double dragX = 0;
    private double drawY = 0;

    
    @FXML public AnchorPane root;
    @FXML public ImageView background;
    
    @FXML public HBox decorations;
    @FXML public ImageView minimizeBtn;

    @FXML public ImageView settingBtn;

    @FXML public ImageView exitBtn;

    @FXML
    public abstract void openSettings(MouseEvent event);
    
    @FXML 
    public void initialize() {
    	ColorAdjust ca = new ColorAdjust(1, 1, 0, 0);
    	ca.setInput(new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 20, 0.1f, 0, 0));
    	decorations.getChildren().forEach(n -> {
    		n.setOnMouseEntered(e -> {
    			n.setEffect(ca);
    		});
    		n.setOnMouseExited(e -> {
    			n.setEffect(null);
    		});
    	});
    }


    @FXML
    public void minimize(MouseEvent event) {
		var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.setIconified(true);
    }

    
    @FXML
    public void exit(MouseEvent event) {
    	Log.info("on exit");
		Coraline.credentials = new String[2];
		
    	if(AmethystApp.stage.getScene() == AmethystApp.loginScene) {
    		Platform.exit(); 
    		//System.exit(0);
    	} else {
        	AmethystApp.stage.setScene(AmethystApp.loginScene);
    	}
    }
    

    @FXML
    public void onPress(MouseEvent event) {
		var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		dragX = stage.getX() - event.getScreenX();
		drawY = stage.getY() - event.getScreenY();
    }
    @FXML
    public void onDrag(MouseEvent event) {
    	var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.setX(event.getScreenX() + dragX);
        stage.setY(event.getScreenY() + drawY);
    }

    
}
