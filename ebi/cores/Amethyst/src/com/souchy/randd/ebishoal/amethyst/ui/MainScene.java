package com.souchy.randd.ebishoal.amethyst.ui;

import com.souchy.randd.ebishoal.amethyst.main.AmethystApp;
import com.souchy.randd.ebishoal.amethyst.ui.tabs.Collection;
import com.souchy.randd.ebishoal.amethyst.ui.tabs.DraftController;
import com.souchy.randd.ebishoal.amethyst.ui.tabs.Home;
import com.souchy.randd.ebishoal.amethyst.ui.tabs.Profile;
import com.souchy.randd.ebishoal.amethyst.ui.tabs.Settings;
import com.souchy.randd.ebishoal.amethyst.ui.tabs.Shop;
import com.souchy.randd.ebishoal.amethyst.ui.tabs.Teams;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class MainScene extends Common {

    @FXML public ListView<?> chatHistory;
    @FXML public TextField chatField;
    
    @FXML public TabPane mainTabs;
    @FXML public Tab homeTab;
    @FXML public Tab profileTab;
    @FXML public Tab teamsTab;
    @FXML public Tab collectionTab;
    @FXML public Tab shopTab;
    @FXML public Tab settingsTab;
    @FXML public Tab draftTab;
    
    
    @FXML public Home homeController;
    @FXML public Profile profileController;
    @FXML public Teams teamsController;
    @FXML public Collection collectionController;
    @FXML public Shop shopController;
    @FXML public Settings settingsController;
    @FXML public DraftController draftController;
    
    /** 
     * garde en mémoire le tab sur lequel on était avant d'ouvrir les settings pour pouvoir le rouvrir par après 
     */
    private Tab tabBeforeSettings;
    
    @FXML
    public void initialize() {
    	super.initialize();
    	AmethystApp.mainController = this;
    }

    @Override
    public void openSettings(MouseEvent event) {
    	var current = mainTabs.getSelectionModel().getSelectedItem();
    	if(current != settingsTab) {
        	tabBeforeSettings = current;
        	mainTabs.getSelectionModel().select(settingsTab); 
    	} else if(tabBeforeSettings != null)  {
        	mainTabs.getSelectionModel().select(tabBeforeSettings); 
        	tabBeforeSettings = null;
    	}
    }
    

    @FXML
    public void chatBoxOnKey(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER) {
    		// send msg
    	}
    }
    
    @FXML
    public void openChat(MouseEvent event) {
    	chatHistory.setVisible(!chatHistory.isVisible());
    	chatField.setVisible(!chatField.isVisible());
    }

    
    
}
