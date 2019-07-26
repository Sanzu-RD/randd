package com.souchy.randd.tools.rainbow.ui;

import com.souchy.randd.tools.rainbow.main.Rainbow;
import com.souchy.randd.tools.rainbow.main.RainbowApp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public abstract class Common {

    private double dragX = 0;
    private double drawY = 0;

    
	@FXML
	public HBox decorations;
	
	@FXML
	public void minimize(MouseEvent event) {
		var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}
	
	// @FXML
	// public void openSettings(MouseEvent event) {
	// var current = mainTabs.getSelectionModel().getSelectedItem();
	// if(current != settingsTab) {
	// tabBeforeSettings = current;
	// mainTabs.getSelectionModel().select(settingsTab);
	// } else if(tabBeforeSettings != null) {
	// mainTabs.getSelectionModel().select(tabBeforeSettings);
	// tabBeforeSettings = null;
	// }
	// }

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
    
	@FXML
	public void exit(MouseEvent event) {
		if(RainbowApp.stage.getScene() == RainbowApp.loginScene) {
			Platform.exit(); // System.exit(0);
			Rainbow.core.client.stop();
		} else {
			RainbowApp.stage.setScene(RainbowApp.loginScene);
		}
	}
	
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
	public abstract void openSettings(MouseEvent event);
}
