package com.souchy.randd.modules.monitor.ui;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.souchy.randd.modules.base.BaseModuleInformation;
import com.souchy.randd.modules.monitor.main.Main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;

public class Root extends VBox {
	
	@FXML private ResourceBundle resources;
	@FXML private URL location;
	@FXML private Font x1;
	@FXML private Color x2;
	@FXML private Font x11;
	@FXML private Color x21;
	@FXML private Font x12;
	@FXML private Color x22;
	@FXML private Font x3;
	@FXML private Color x4;

	@FXML private MenuItem menuFileOpen;
	@FXML private Label statusRight;
	@FXML private Label statusLeft;
	@FXML private ToggleButton btnToggle;
	@FXML private Button btn1;
	@FXML private Button btn2;
	@FXML private ListView<String> listModules;
	@FXML private ListView<String> listProps;
	@FXML private Label labelName;
    @FXML private AnchorPane detailsPane;

	private final DirectoryChooser chooser = new DirectoryChooser();
	
	public Root() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("root.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	
	
	@FXML
	void initialize() {
        assert x1 != null : "fx:id=\"x1\" was not injected: check your FXML file 'root.fxml'.";
        assert x2 != null : "fx:id=\"x2\" was not injected: check your FXML file 'root.fxml'.";
        assert listModules != null : "fx:id=\"listModules\" was not injected: check your FXML file 'root.fxml'.";
        assert detailsPane != null : "fx:id=\"detailsPane\" was not injected: check your FXML file 'root.fxml'.";
        assert labelName != null : "fx:id=\"labelName\" was not injected: check your FXML file 'root.fxml'.";
        assert x11 != null : "fx:id=\"x11\" was not injected: check your FXML file 'root.fxml'.";
        assert x21 != null : "fx:id=\"x21\" was not injected: check your FXML file 'root.fxml'.";
        assert btnToggle != null : "fx:id=\"btnToggle\" was not injected: check your FXML file 'root.fxml'.";
        assert btn1 != null : "fx:id=\"btn1\" was not injected: check your FXML file 'root.fxml'.";
        assert btn2 != null : "fx:id=\"btn2\" was not injected: check your FXML file 'root.fxml'.";
        assert listProps != null : "fx:id=\"listProps\" was not injected: check your FXML file 'root.fxml'.";
        assert x12 != null : "fx:id=\"x12\" was not injected: check your FXML file 'root.fxml'.";
        assert x22 != null : "fx:id=\"x22\" was not injected: check your FXML file 'root.fxml'.";
        assert statusLeft != null : "fx:id=\"statusLeft\" was not injected: check your FXML file 'root.fxml'.";
        assert x3 != null : "fx:id=\"x3\" was not injected: check your FXML file 'root.fxml'.";
        assert x4 != null : "fx:id=\"x4\" was not injected: check your FXML file 'root.fxml'.";
        assert statusRight != null : "fx:id=\"statusRight\" was not injected: check your FXML file 'root.fxml'.";
        assert menuFileOpen != null : "fx:id=\"menuFileOpen\" was not injected: check your FXML file 'root.fxml'.";

		//chooser.getExtensionFilters().addAll(new ExtensionFilter("Excel Document", "*.xls"));
		chooser.setInitialDirectory(new File(System.getProperty("user.home")));

		menuFileOpen.setOnAction(e -> {
            File in =  chooser.showDialog(Main.stage); //.showDialog(WorkScheduler.stage);
            //open(selectedFile);
    		if(in == null) return;
    		Main.stage.titleProperty().set("Module Monitor - " + in.getPath());
    		Main.manager.addDirectory(in);//.loadModuleList(in);
    		Map<String, BaseModuleInformation> modulesList = Main.manager.getModulesInfoList();
    		listModules.getItems().clear();
    		//listModules.getItems().addAll((String[]) modulesList.keySet().stream().map(k -> k.getName()).toArray()); //.collect(Collectors.toList()));
    		modulesList.values().forEach(k -> listModules.getItems().add(k.getName()));
		});
		listModules.setOnMouseClicked(e -> {
			int i = listModules.getSelectionModel().getSelectedIndex();
			BaseModuleInformation o = (BaseModuleInformation) Main.manager.getModulesInfoList().values().toArray()[i];
			labelName.setText(o.getName());
			listProps.getItems().clear();
			listProps.getItems().addAll(o.getProps());
		});
		btn1.setOnAction(e -> {
			//Main.load
		});
		
		// C:\Users\Robyn\Documents\eclipse-mars\workspaces\r and d\MyDummyPlugin
		/*listModules.selectionModelProperty().addListener(e -> {
			//String fileName = listModules.getSelectionModel().getSelectedItem();
			int i = listModules.getSelectionModel().getSelectedIndex();
			BaseModuleInformation o = (BaseModuleInformation) Main.manager.getModulesInfoList().values().toArray()[i];
			labelName.setText(o.getName());
			listProps.getItems().clear();
			listProps.getItems().addAll(o.getProps());
		});*/
	}

	private void open(File in){
		if(in == null) return;
		Main.stage.titleProperty().set(in.getName());
		//this.out = out;
		//ExcelIO.open(in, data);
	}
	
	
	
}
