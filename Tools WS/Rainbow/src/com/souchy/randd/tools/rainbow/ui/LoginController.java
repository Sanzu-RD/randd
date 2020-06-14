package com.souchy.randd.tools.rainbow.ui;

import com.souchy.randd.commons.deathebi.msg.GetSalt;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.Authenticate;
import com.souchy.randd.tools.rainbow.main.Rainbow;
import com.souchy.randd.tools.rainbow.main.RainbowApp;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class LoginController extends Common {


    @FXML public TextField usernameField;
    @FXML public PasswordField passwordField;

    public LoginController() {
    	RainbowApp.loginController = this;
	}

    @FXML
    public void login(MouseEvent event) {
//    	var packet = new Authenticate(usernameField.getText(), passwordField.getText());
    	
    	Rainbow.client.write(new GetSalt(usernameField.getText()));
    	
//		System.out.println("received : " + Test.get());
//		var response = Opaline.client.target("http://localhost:8080").request().get();
//		System.out.println(response);
//		System.out.println(response.readEntity(String.class));
    }

//    @Override
//    public void openSettings(MouseEvent event) {
//    	
//    }
    
    @FXML
    public void onRootKeyRelease(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER) {
    		login(null);
    	}
   }
    

    
}
