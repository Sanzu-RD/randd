package com.souchy.randd.tools.rainbow.ui;

import java.time.Period;
import java.util.Date;
import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.commons.tealwaters.logging.Logging;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.AskCreate;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.AskKillNode;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.AskNodes;
import com.souchy.randd.deathshadows.pearl.NodeInfo;
import com.souchy.randd.tools.rainbow.main.Rainbow;
import com.souchy.randd.tools.rainbow.main.RainbowApp;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainController extends Common {

	
	// show list of nodes
	
	// ability to create nodes
	
	// create nodes, get node info (uptime, clients, #fights, etc), kill nodes
	
	@FXML private MenuItem btnOpal;
	@FXML private MenuItem btnCoral;
	@FXML private MenuItem btnMoonstone;
	@FXML private MenuItem btnBeryl;
	@FXML private MenuItem btnAmethyst;
	@FXML private MenuItem btnSapphire;
	@FXML private Menu btnSetRoot;
	
	@FXML private TextField consoleInput;
	@FXML private TextArea consoleText;
	
	private ObservableList<NodeInfo> nodes = FXCollections.observableArrayList(); 
	@FXML private ListView<NodeInfo> nodeList;
	
	@FXML private AnchorPane nodeProperties;
	@FXML private Label selectedTitle;
	@FXML private Label ipport;
	@FXML private Label clientCount;
	@FXML private Label launchTime;
	@FXML private Label uptime;
	@FXML private Label heartbeatTime;
	@FXML private Button btnKill;
	
	public MainController() {
		RainbowApp.mainController = this;
		Rainbow.core.bus.register(this);
	}

	/**
	 * EventHandler for AskNodes packet 
	 */
	@Subscribe
	public void handleAskNodes(AskNodes nodes) {
		Platform.runLater(() -> {
			this.nodes.clear();
			this.nodes.addAll(nodes.nodes);
		});
	}
	
	@FXML
	@Override
	public void initialize() {
//		super.initialize();
		Logging.streams.add(log -> Platform.runLater(() -> consoleText.appendText("\n" + log.toString())));
		nodeList.setItems(nodes);
		nodeList.getSelectionModel().selectedItemProperty().addListener(this::select);
		
		btnOpal.setOnAction(event -> {
			Rainbow.client.write(new AskCreate("opal"));
		});
		btnCoral.setOnAction(event -> {
			Rainbow.client.write(new AskCreate("coral"));
		});
		btnMoonstone.setOnAction(event -> {
			Rainbow.client.write(new AskCreate("blackmoonstone"));
		});
		btnBeryl.setOnAction(event -> {
			Rainbow.client.write(new AskCreate("beryl"));
		});
		btnAmethyst.setOnAction(event -> {
		});
		btnSapphire.setOnAction(event -> {
		});
		btnSetRoot.setOnAction(event -> {
			
		});
	}

	@FXML
	void onRootKeyReleased(KeyEvent event) {
		// ask for data refresh
		if(event.getCode() == KeyCode.F5) {
			askRefresh(null);
		}
    }

    @FXML
	void askRefresh(ActionEvent event) {
		// ask for data refresh
		Rainbow.client.write(new AskNodes());
	}

	@FXML
	void kill(MouseEvent event) {
		// ask for node kill
		try {
			Rainbow.client.write(new AskKillNode(nodeList.getSelectionModel().selectedItemProperty().get().id));
		} catch (Exception e) {
			Log.error("", e);
		}
	}
    
    /**
     * When node list selection changes
     */
    public void select(ObservableValue<? extends NodeInfo> observable, NodeInfo oldValue, NodeInfo newValue) {
    	if(newValue == null) return;
    	var startdate = new Date(newValue.launchTime);
    	var uptimed = new Date(System.currentTimeMillis() - newValue.launchTime);
    	var lastbeat = new Date(newValue.lastHeartbeatTime);
    	
    	selectedTitle.setText(newValue.toString());
    	ipport.setText("URL : " + newValue.url());
    	clientCount.setText("Client count : " + newValue.clientCount);
    	launchTime.setText("Launch time : " + startdate);
    	uptime.setText("Uptime : " + uptimed);
    	heartbeatTime.setText("Last heartbeat : " + lastbeat);
    }
    
}
