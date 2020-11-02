package com.souchy.randd.tools.rainbow.ui;

import java.io.File;
import java.io.IOException;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.AskNodes;
import com.souchy.randd.deathshadows.pearl.NodeInfo;
import com.souchy.randd.tools.rainbow.main.Rainbow;
import com.souchy.randd.tools.rainbow.ui.events.RefreshEvent;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

// com.souchy.randd.tools.rainbow.ui.NodeDetails
public class NodeDetails extends ScrollPane {
	
	public NodeInfo node;
	
	@FXML private Label lblUrl;
	@FXML private Label lblClients;
	@FXML private Label lblLaunchTime;
	@FXML private Label lblUptime;
	@FXML private Label lblHeartbeat;
	@FXML private Label lblCPU;
	@FXML private Label lblRAM;
	
	public NodeDetails(NodeInfo node) {
		try {
			FXMLLoader loader = new FXMLLoader(new File("res/ux/rainbow/nodedetails.fxml").toURI().toURL());
			loader.setController(this);
//			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			Log.error("", e);
		}
		Rainbow.core.bus.register(this);
		this.node = node;
		this.refresh();
	}
	
//	@Subscribe
//	public void onRefresh(RefreshEvent r) {
//		if(r.node == this) {
//			
//		}
//	}
	
	private void refresh() {
		this.lblUrl.setText(node.url());
		this.lblClients.setText(Integer.toString(node.getClients()));
		this.lblLaunchTime.setText(Long.toString(node.getLaunchTime()));
		this.lblUptime.setText(Long.toString(node.getUptime()));
		this.lblHeartbeat.setText(Long.toString(node.lastHeartbeatTime));
		this.lblCPU.setText(Double.toString(node.cpu));
		this.lblRAM.setText(Long.toString(node.ram));
	}

	/**
	 * EventHandler for AskNodes packet
	 */
	@Subscribe
	public void handleAskNodes(AskNodes nodes) {
		Log.info("NodeTable handleAskNodes " + nodes.nodes.size());
		for (var node : nodes.nodes) {
			if(node.id == this.node.id) {
				this.node = node;
				Platform.runLater(this::refresh);
			}
		}
	}
	
}
