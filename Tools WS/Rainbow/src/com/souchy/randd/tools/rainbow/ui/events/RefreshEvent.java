package com.souchy.randd.tools.rainbow.ui.events;

import javafx.scene.Node;

public class RefreshEvent {
	public Node node;
	public RefreshEvent(Node target) {
		this.node = target;
	}
	
}
