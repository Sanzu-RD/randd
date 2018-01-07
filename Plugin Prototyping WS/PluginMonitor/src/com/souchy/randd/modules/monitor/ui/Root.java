package com.souchy.randd.modules.monitor.ui;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import com.hiddenpiranha.commons.tealwaters.io.files.FilesManager;
import com.souchy.randd.modules.base.BaseModule;
import com.souchy.randd.modules.base.BaseModuleInformation;
import com.souchy.randd.modules.monitor.main.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
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
    @FXML private TextArea console;

	private final DirectoryChooser chooser = new DirectoryChooser();
	
	public Root() {
		try {
			FXMLLoader loader = new FXMLLoader(FilesManager.get().getResource("root.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	private Set<BaseModule> persistanceTest = new HashSet<>();
	
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
        assert console != null : "fx:id=\"console\" was not injected: check your FXML file 'root.fxml'.";

        File lastDir = new File(App.config.lastDirectory.get()); // PluginMonitorConfig.lastDirectory.get()
        
		//chooser.getExtensionFilters().addAll(new ExtensionFilter("Excel Document", "*.xls"));
		chooser.setInitialDirectory(lastDir); //System.getProperty("user.home")));
		
		menuFileOpen.setOnAction(e -> {
            File in =  chooser.showDialog(App.stage); //.showDialog(WorkScheduler.stage);
            open(in);
		});
		listModules.setOnMouseClicked(e -> {
			BaseModuleInformation info = this.getSelectedInfo();
			if(info != null) updateDetails(info);
		});
		//ToggleGroup group = new ToggleGroup();
		//btnToggle.setToggleGroup(group);
		String instantiate = "Load / Instantiate";
		String dispose = "Unload / Dispose";
		btnToggle.setText(instantiate);
		btnToggle.setOnAction(e -> {
			//System.out.println("kmjuiy : [" + btnToggle.getText()+"]");
			if(btnToggle.getText().equals(instantiate)) {
				BaseModuleInformation info = this.getSelectedInfo();
				BaseModule module = null;
				try {
					module = App.manager.instanciate(info);
				} catch (Exception e1) {
					//e1.printStackTrace();
					print(e1.getMessage());
					return;
				}
				btnToggle.setText(dispose);
				persistanceTest.add(module);
				btn1.setDisable(module == null);
				print("btn load, persist size : " + persistanceTest.size());
			} else 
			if(btnToggle.getText().equals(dispose)) {
				btnToggle.setText(instantiate);
				BaseModuleInformation info = this.getSelectedInfo();
				boolean b = App.manager.dispose(info);
				//btn1.setDisable(b);{
				print("btn unload : " + b);
			}
		});
		/*btnToggle.setText("Allo");
		group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) -> {
			if(btnToggle.getText() == "Load") {
				System.out.println("btn load");
				btnToggle.setText("Unload");
				int i = listModules.getSelectionModel().getSelectedIndex();
				BaseModuleInformation info = App.manager.getInfo(i); 
				BaseModule module = App.manager.load(info);
				btn1.setDisable(module == null);
			} else
			if(btnToggle.getText() == "Unload") {
				System.out.println("btn unload");
				btnToggle.setText("Load");
				int i = listModules.getSelectionModel().getSelectedIndex();
				BaseModuleInformation info = App.manager.getInfo(i); 
				boolean b = App.manager.remove(info);
				//btn1.setDisable(b);
			}
		});*/
		btn1.setOnAction(e -> {
			BaseModule module = App.manager.get(getSelectedInfo());
			if (module != null)
				print(module.doSomething());
			else
				print("cant because module not loaded");
		});
		btn2.setOnAction(e -> {
			btnToggle.setText("Load");
		});
		// C:\Users\Robyn\Documents\eclipse-mars\workspaces\r and d\MyDummyPlugin
		
		open(lastDir);
	}
	
	private BaseModuleInformation getSelectedInfo() {
		//int i = listModules.getSelectionModel().getSelectedIndex();
		String name = listModules.getSelectionModel().getSelectedItem();
		BaseModuleInformation info = App.manager.getInfo(name); 
		//System.out.println("click : name=["+name+"]");
		return info;
	}
	
	private void print(String str) {
		console.appendText("[" + new Date() + "] : " + str + "\n");
	}
	
	private void open(File in){
		if(in == null) return;
		App.stage.titleProperty().set(App.config.windowTitle + in.toString());
		/*System.out.println(in.toString());
		System.out.println(in.getAbsolutePath());
		System.out.println(in.getPath());*/
		
		App.config.lastDirectory.set(in.getAbsolutePath());
		App.manager.explore(in);//.loadModuleList(in);
		listModules.getItems().clear();
		App.manager.getInfos().forEach(k -> listModules.getItems().add(k.getName()));
		App.config.save();

		updateDetails(null);
	}
	
	public void updateDetails(BaseModuleInformation info) {
		if(info == null) {
			labelName.setText("<select a plugin>");
			listProps.getItems().clear();
			// addAll
			detailsPane.setDisable(true);
			//btn1.setDisable(true);
		} else {
			labelName.setText(info.getName());
			listProps.getItems().clear();
			listProps.getItems().addAll(info.getProps());
			detailsPane.setDisable(false);
			BaseModule module = App.manager.get(info);
			btn1.setDisable(module == null);
			//btn1.setDisable(false);
		}
	}
	
	
}
