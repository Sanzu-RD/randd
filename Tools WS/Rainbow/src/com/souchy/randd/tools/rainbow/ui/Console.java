package com.souchy.randd.tools.rainbow.ui;

import java.io.File;
import java.io.IOException;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.commons.tealwaters.logging.Logging;
import com.souchy.randd.tools.rainbow.main.Rainbow;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class Console { // extends VBox {
	
	@FXML
	private VBox root;
	@FXML
	private TextArea consoleText;
	@FXML
	private TextField consoleInput;
	
	public Console() {
		Log.info("Console ctor");
		Rainbow.core.bus.register(this);
    	
//        try {
//            FXMLLoader loader = new FXMLLoader(new File("res/ux/rainbow/console.fxml").toURI().toURL());
//            if(loader.getController() == null) 
//            	loader.setController(this);
//            loader.setRoot(this);
//            loader.load();
//        } catch (IOException exc) {
//            // handle exception
//        }
    }
    
    @FXML
    void initialize() {
    	Log.info("Console init");
    	
		Logging.streams.add(log -> Platform.runLater(() -> consoleText.appendText("\n" + log.toString())));
    }
    
}
