package com.souchy.randd.tools.rainbow.ui.tools;


import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class Anchors {

	public static void fill(Node child) {
    	Anchors.setAnchor(child, 0, 0, 0, 0);
	}
	
    public static void setAnchor(Node child, double top, double right, double bottom, double left) {
    	AnchorPane.setTopAnchor(child, top);
    	AnchorPane.setRightAnchor(child, right);
    	AnchorPane.setBottomAnchor(child, bottom);
    	AnchorPane.setLeftAnchor(child, left);
    }
    
}
