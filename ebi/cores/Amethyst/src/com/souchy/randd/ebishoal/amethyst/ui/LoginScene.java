package com.souchy.randd.ebishoal.amethyst.ui;

import com.souchy.randd.ebishoal.amethyst.ui.components.LoginForm;
import com.souchy.randd.ebishoal.amethyst.ui.components.RegisterForm;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class LoginScene extends Common {

    private String registerStr = "Register >";
    private String loginStr = "< Log In";
    
    @FXML
    public LoginForm loginFormController;
    @FXML 
    public RegisterForm registerFormController;

    @FXML public Button switchFormBtn;

    
    @FXML 
    @Override
    public void initialize() {
    	super.initialize();
    	if(registerFormController != null) registerFormController.root.setVisible(false);
    }
    
    @FXML
    public void rememberAccount(MouseEvent event) {
    	
    }
    
    @FXML
    public void switchForm(MouseEvent event) {
    	loginFormController.root.setVisible(!loginFormController.root.isVisible());
    	registerFormController.root.setVisible(!registerFormController.root.isVisible());
    	if(switchFormBtn.getText().equals(registerStr)) {
    		switchFormBtn.setText(loginStr);
    	} else {
    		switchFormBtn.setText(registerStr);
    	}
    }
    
    @FXML
    @Override
    public void openSettings(MouseEvent event) {
    	
    }
    
	@FXML
	public void onRootKeyRelease(KeyEvent event) {
//		if(loginFormController.root.isVisible())
//			loginFormController.onRootKeyRelease(event);
//		if(registerFormController.root.isVisible())
//			registerFormController.onRootKeyRelease(event);
	}
	
    /*
  //  @FXML
    public void forgotAccountFromEmail() {
    	var email = emailField.getText();
    	Opaline.auth.forgotAccountFromEmail(email);
    }
  //  @FXML
    public void forgotAccountFromPhone() {
    	var phone = phoneField.getText();
    	Opaline.auth.forgotAccountFromPhone(phone);
    }
    */
    
}
