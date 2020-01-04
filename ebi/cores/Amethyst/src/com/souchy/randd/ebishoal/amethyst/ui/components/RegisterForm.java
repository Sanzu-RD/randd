package com.souchy.randd.ebishoal.amethyst.ui.components;

import com.souchy.randd.commons.opal.IAuthentication;
import com.souchy.randd.commons.opal.IAuthentication.RegistrationToken;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.opaline.api.Opaline;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class RegisterForm { //extends VBox {
	
	public RegisterForm() {
//		try {
//			FXMLLoader loader = new FXMLLoader(FilesManager.getResource("components/registerForm.fxml"));
//			loader.setController(this);
//			loader.setRoot(this);
//			loader.load();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	@FXML public VBox root;

    @FXML public TextField pseudoField;

    @FXML public TextField usernameField;

    @FXML public TextField emailField;

    //@FXML public TextField emailConfirmField;

    @FXML public PasswordField passwordField;

    @FXML public Button signupBtn;

    @FXML private Label helpLbl;
    
	@FXML
	public void onRootKeyRelease(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {
			signup(null);
		}
	}
	
    @FXML
    public void signup(MouseEvent event) {
    	var pseudo = pseudoField.getText();
    	var username = usernameField.getText();
    	var password = passwordField.getText();
    	var email = emailField.getText();
    	Log.info("RegisterForm.signup : " + String.format("{ %s, %s, %s, %s }", pseudo, username, password, email));
    	if(IAuthentication.validSignupInput(pseudo, username, password, email)) {
        	Log.info("RegisterForm.signup : valid input.");
        	var salt = IAuthentication.generateSalt();
        	var hashedPassword = IAuthentication.hashPassword(password, salt);
        	var user = Opaline.auth.signup(new RegistrationToken(pseudo, username, hashedPassword, salt, email));
        	Log.info("RegisterForm.signup : user = "+user+".");
        	if(user != null) {
        		// success
            	Log.info("RegisterForm.signup : success.");
        	} else {
        		// server didnt accept the user creation
            	Log.info("RegisterForm.signup : disapproved.");
        	}
    	} else {
    		// invalid input
        	Log.info("RegisterForm.signup : invalid input.");
    	}
    	if(event != null) event.consume();
    }
    
    
    @FXML
    public void initialize() {
        assert pseudoField != null : "fx:id=\"pseudoField\" was not injected: check your FXML file 'registerForm.fxml'.";
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'registerForm.fxml'.";
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'registerForm.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'registerForm.fxml'.";
		assert signupBtn != null : "fx:id=\"signupBtn\" was not injected: check your FXML file 'registerForm.fxml'.";
		assert helpLbl != null : "fx:id=\"helpLbl\" was not injected: check your FXML file 'registerForm.fxml'.";
		var tip = new Tooltip("Account creation is limited at 1 per 30 seconds. Accounts with an unconfirmed email expire after 24 hours.");
		tip.setShowDelay(Duration.ZERO);
		helpLbl.setTooltip(tip);
		//Log.info("initialized register form.");
    }
    
}
