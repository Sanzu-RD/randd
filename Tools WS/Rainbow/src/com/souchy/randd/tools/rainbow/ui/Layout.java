package com.souchy.randd.tools.rainbow.ui;

import java.io.File;
import java.io.IOException;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.blackmoonstone.main.BlackMoonstone;
import com.souchy.randd.deathshadows.coral.main.Coral;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.AskCreate;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.AskNodes;
import com.souchy.randd.deathshadows.opal.Opal;
import com.souchy.randd.deathshadows.pearl.NodeInfo;
import com.souchy.randd.deathshadows.pearl.main.Pearl;
import com.souchy.randd.tools.rainbow.main.Rainbow;
import com.souchy.randd.tools.rainbow.main.RainbowApp;
import com.souchy.randd.tools.rainbow.ui.events.Connected;
import com.souchy.randd.tools.rainbow.ui.events.Kill;
import com.souchy.randd.tools.rainbow.ui.events.RefreshEvent;
import com.souchy.randd.tools.rainbow.ui.tools.Anchors;
import com.souchy.randd.ebishoal.amethyst.main.Amethyst;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

// com.souchy.randd.tools.rainbow.ui.Layout
public class Layout { //extends BorderPane {
	
	// root
	@FXML private BorderPane root;
	// menu
	@FXML private MenuBar menu;
	// 
	@FXML private CheckBox chkConnected;
	
	// views
	@FXML private Menu menuView;
	@FXML private MenuItem btnViewMain;
	@FXML private RadioMenuItem btnViewConsole;
	@FXML private Menu menuNodeView; // list of nodes to go to a specific one
	
	// actions
	@FXML private Menu menuEdit;
	@FXML private MenuItem btnRefresh;
	@FXML private MenuItem btnRestart;
	@FXML private MenuItem btnShutdown;
	@FXML private MenuItem btnKill;
	
	// new
	@FXML private Menu menuNew;
	// new death shadow
	@FXML private MenuItem btnOpal;
	@FXML private MenuItem btnCoral;
	@FXML private MenuItem btnMoonstone;
	@FXML private MenuItem btnBeryl;
	// new ebi shoal
	@FXML private MenuItem btnAmethyst;
	@FXML private MenuItem btnSapphire;

	// new mock
	@FXML private Menu menuNewMock;
	// new death shadow
	@FXML private MenuItem btnOpalMock;
	@FXML private MenuItem btnCoralMock;
	@FXML private MenuItem btnMoonstoneMock;
	@FXML private MenuItem btnBerylMock;
	// new ebi shoal
	@FXML private MenuItem btnAmethystMock;
	@FXML private MenuItem btnSapphireMock;
	
	// packets
	@FXML private Menu menuPacket;
	
	// network
	@FXML private Menu menuNetwork;
	@FXML private MenuItem btnConnect;
	@FXML private MenuItem btnPearl;
	@FXML private ToggleGroup networkToggleGroup;
	@FXML private RadioMenuItem btnLocal;
	@FXML private RadioMenuItem btnLan;
	@FXML private RadioMenuItem btnExternal;
	
	// content / console
	@FXML private SplitPane split;
	@FXML private AnchorPane contentPane;
	@FXML private AnchorPane consolePane;
	
	@FXML private VBox console;
	@FXML private Console consoleController;
	

	public Layout() {
		Log.info("Layout ctor");
		RainbowApp.mainController = this;
		Rainbow.core.bus.register(this);
		
//		try {
//			FXMLLoader loader = new FXMLLoader(new File("res/ux/rainbow/layout.fxml").toURI().toURL());
//			loader.setController(this);
//			loader.setRoot(this);
//			loader.load();
//		} catch (IOException e) {
//			Log.error("", e);
//		}
	}
	
	
    @FXML
    void initialize() {
    	try {
			
			Log.info("Layout init, cons = " + "" + " + " + consoleController);
			// content.getChildren().add(RainbowApp.getNodeTable());
			
			chkConnected.setSelected(Rainbow.client != null);
			
			btnViewConsole.setOnAction(this::toggleConsole);
//			toggleConsole(null);
			
			btnRefresh.setOnAction(this::refresh);
			root.setOnKeyReleased(this::onRootKeyReleased);
			
			// add node table
			var table = RainbowApp.getNodeTable();
			contentPane.getChildren().add(table);
			Anchors.fill(table);

			// add console
			this.console = (VBox) RainbowApp.getConsole();
			consolePane.getChildren().add(console);
			Anchors.fill(console);

			btnKill.setOnAction(e -> Rainbow.core.bus.post(new Kill()));
			
			btnMoonstone.setOnAction(e -> Rainbow.client.write(new AskCreate(BlackMoonstone.class.getSimpleName())));
			btnCoral.setOnAction(e -> Rainbow.client.write(new AskCreate(Coral.class.getSimpleName())));
			btnOpal.setOnAction(e -> Rainbow.client.write(new AskCreate(Opal.class.getSimpleName())));
//			btnBeryl.setOnAction(e -> Rainbow.client.write(new AskCreate(GreenBeryl.class.getSimpleName()))));
			btnAmethyst.setOnAction(e -> Pearl.createProcess(Amethyst.class, ""));
			btnSapphire.setOnAction(e -> Pearl.createProcess(SapphireOwl.class, ""));


			btnMoonstoneMock.setOnAction(e -> Rainbow.client.write(new AskCreate(BlackMoonstone.class.getSimpleName(), "mock")));
			btnCoralMock.setOnAction(e -> Rainbow.client.write(new AskCreate(Coral.class.getSimpleName())));
			btnOpalMock.setOnAction(e -> Rainbow.client.write(new AskCreate(Opal.class.getSimpleName())));
//			btnBerylMock.setOnAction(e -> Rainbow.client.write(new AskCreate(GreenBeryl.class.getSimpleName()))));
			btnAmethystMock.setOnAction(e -> Pearl.createProcess(Amethyst.class, ""));
			btnSapphireMock.setOnAction(e -> Pearl.createProcess(SapphireOwl.class, "prod localhost 6000 souchy z 1"));
			
			
			btnConnect.setOnAction(e -> Rainbow.core.connect());
			btnPearl.setOnAction(e -> Pearl.createDeathShadow(Pearl.class, "../"));
			btnLocal.setOnAction(e -> Rainbow.core.host = "localhost");
			btnLan.setOnAction(e -> Rainbow.core.host = "192.168.2.15");
			btnExternal.setOnAction(e -> Rainbow.core.host = "vyxyn.ddns.net");
			btnLocal.setSelected(true);
			
			btnViewMain.setOnAction(this::viewOverview);
			
		} catch (Exception ex) {
			Log.error("", ex);
		}
    }

	private void onRootKeyReleased(KeyEvent event) {
		// ask for data refresh
		if(event.getCode() == KeyCode.F5) {
			refresh(null);
		}
    }
	private void refresh(ActionEvent e) {
		Rainbow.core.bus.post(new RefreshEvent(contentPane.getChildren().get(0)));
	}

    
    private void toggleConsole(ActionEvent e) {
		var sel = btnViewConsole.isSelected();
		console.setVisible(sel);
		if(sel) {
			split.setDividerPositions(0.75);
			split.getItems().add(1, consolePane);
//			split.setStyle("");
		} else {
			split.getItems().remove(consolePane);
//			split.setDividerPositions(1);
//			split.setStyle(".split-pane-divider { -fx-background-color: transparent; }");
		}
    }

	/**
	 * EventHandler for AskNodes packet 
	 */
	@Subscribe
	public void handleAskNodes(AskNodes nodes) {
		Platform.runLater(() -> {
			nodes.nodes.forEach(n -> {
				if(n.port != 0) {
				}
			});
		});
	}

    public void viewOverview(ActionEvent e) {
    	
    }
    public void viewNode(NodeInfo node) {
    	this.consolePane.getChildren().set(0, new NodeDetails(node));
    }
    
    
    @Subscribe
    public void onConnected(Connected event) {
    	Platform.runLater(() -> chkConnected.setSelected(event.connected));
    }
    
}
