package com.souchy.randd.tools.rainbow.ui;

import com.souchy.randd.tools.rainbow.main.Rainbow;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

public class PacketEditor { // extends AnchorPane {

	@FXML private AnchorPane root;
	
    @FXML private Label lblTarget;

    @FXML private WebView webview;

    public PacketEditor() {
		Rainbow.core.bus.register(this);
	}
    
    @FXML
    void initialize() {

    }
	
}
