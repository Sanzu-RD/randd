package com.souchy.randd.ebishoal.amethyst.ui;

import com.souchy.randd.ebishoal.amethyst.ui.tabs.Collection;
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
    @FXML public Tab settingsTab;
    
    
    @FXML Home homeController;
    @FXML Profile profileController;
    @FXML Teams teamsController;
    //@FXML TeamEditor teamEditorController;
    @FXML Collection collectionController;
    @FXML Shop shopController;
    @FXML Settings settingsController;
    

    /** garde en mémoire le tab sur lequel on était avant d'ouvrir les settings pour pouvoir le rouvrir par après */
    private Tab tabBeforeSettings;

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
