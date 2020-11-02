package com.souchy.randd.tools.rainbow.ui;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.commons.tealwaters.logging.Logging;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.AskKillNode;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.AskNodes;
import com.souchy.randd.deathshadows.pearl.NodeInfo;
import com.souchy.randd.tools.rainbow.main.Rainbow;
import com.souchy.randd.tools.rainbow.main.RainbowApp;
import com.souchy.randd.tools.rainbow.ui.events.Kill;
import com.souchy.randd.tools.rainbow.ui.events.RefreshEvent;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class NodeTable { // extends VBox {

	private ObservableList<NodeInfo> nodes = FXCollections.observableArrayList(); 
	@FXML public TableView<NodeInfo> root;
	
	public NodeTable() {
		Log.info("NodeTable ctor");
		Rainbow.core.bus.register(this);
		
//		try {
//			FXMLLoader loader = new FXMLLoader(new File("res/ux/rainbow/nodetable.fxml").toURI().toURL());
//			loader.setController(this);
//			loader.setRoot(this);
//			loader.load();
//		} catch (IOException e) {
//			Log.error("", e);
//		}
	}

	@Subscribe
	public void onRefresh(RefreshEvent r) {
		if(r.node == root) {
			// ask for data refresh
			Rainbow.client.write(new AskNodes());
		}
	}
	/**
	 * EventHandler for AskNodes packet 
	 */
	@Subscribe
	public void handleAskNodes(AskNodes nodes) {
		Log.info("NodeTable handleAskNodes " + nodes.nodes.size());
		Platform.runLater(() -> {
			this.nodes.clear();
			nodes.nodes.forEach(n -> {
				if(n.port != 0) {
					Log.info("NodeTable add node " + n);
					this.nodes.add(n);
				}
			});
//			this.nodes.addAll(nodes.nodes);
			this.root.setItems(this.nodes);
		});
	}
	
	@Subscribe
	public void onKill(Kill k) {
		var node = this.root.getSelectionModel().getSelectedItem();
		if(node != null) {
			Rainbow.client.write(new AskKillNode(node.id));
		}
	}
	
    @FXML
    void initialize() {
		Log.info("NodeTable init");

//		root.getColumns().clear();
//		root.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("ip"));

		int c = 0;
		((TableColumn<NodeInfo, Long>) root.getColumns().get(c++)).setCellValueFactory(new PropertyValueFactory<NodeInfo, Long>("id"));
//		((TableColumn<NodeInfo, String>) root.getColumns().get(c++)).setCellValueFactory(new PropertyValueFactory<NodeInfo, String>("ip"));
		((TableColumn<NodeInfo, String>) root.getColumns().get(c++)).setCellValueFactory(e -> new SimpleStringProperty(e.getValue().type.getSimpleName()));
		((TableColumn<NodeInfo, String>) root.getColumns().get(c++)).setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getIp()));
		((TableColumn<NodeInfo, Integer>) root.getColumns().get(c++)).setCellValueFactory(new PropertyValueFactory<NodeInfo, Integer>("port"));
		((TableColumn<NodeInfo, Integer>) root.getColumns().get(c++)).setCellValueFactory(new PropertyValueFactory<NodeInfo, Integer>("clients"));
		((TableColumn<NodeInfo, String>) root.getColumns().get(c++)).setCellValueFactory(e -> new SimpleStringProperty(Long.toString(TimeUnit.MILLISECONDS.toHours(e.getValue().getUptime()))));
//		((TableColumn<NodeInfo, Long>) root.getColumns().get(c++)).setCellValueFactory(new PropertyValueFactory<NodeInfo, Long>("uptime"));
		
		root.setItems(nodes);
		root.getSelectionModel().selectedItemProperty().addListener(this::select);
	}
	

    /**
     * When node list selection changes
     */
    public void select(ObservableValue<? extends NodeInfo> observable, NodeInfo oldValue, NodeInfo newValue) {
    	if(newValue == null) return;
    	var startdate = new Date(newValue.launchTime);
    	var uptimed = new Date(System.currentTimeMillis() - newValue.launchTime);
    	var lastbeat = new Date(newValue.lastHeartbeatTime);
    	
    	RainbowApp.mainController.viewNode(newValue);
    	
//    	selectedTitle.setText(newValue.toString());
//    	ipport.setText("URL : " + newValue.url());
//    	clientCount.setText("Client count : " + newValue.clientCount);
//    	launchTime.setText("Launch time : " + startdate);
//    	uptime.setText("Uptime : " + uptimed);
//    	heartbeatTime.setText("Last heartbeat : " + lastbeat);
    }
    
	
}
