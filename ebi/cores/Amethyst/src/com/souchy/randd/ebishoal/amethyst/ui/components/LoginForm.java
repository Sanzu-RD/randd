package com.souchy.randd.ebishoal.amethyst.ui.components;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.souchy.randd.commons.deathebi.UserUtil;
import com.souchy.randd.commons.opal.IAuthentication;
import com.souchy.randd.commons.opal.IAuthentication.LoginToken;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.amethyst.main.AmethystApp;
import com.souchy.randd.ebishoal.opaline.api.Opaline;
import com.souchy.randd.jade.meta.User;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class LoginForm { //extends VBox {
	
	public LoginForm() {
//		try {
//			var url = FilesManager.getResource("components/loginForm.fxml");
//			FXMLLoader loader = new FXMLLoader(url);
//			loader.setController(this);
//			loader.setRoot(this);
//			loader.load();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	@FXML
	public VBox root;
	
	@FXML
	public TextField usernameField;
	
	@FXML
	public PasswordField passwordField;
	
	@FXML
	public Button loginBtn;
	
	@FXML
	public CheckBox rememberMeChk;
	
	@FXML
	public void onRootKeyRelease(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {
			login(null);
		}
	}

	final Tooltip tip = new Tooltip("Incorrect username/password combination or connection timeout or server fault.");
	
	@FXML
	public void login(MouseEvent event) {
		if(event == null) {
			Log.error("Amethyst.LoginForm.login : event is null");
			return;
		}
		if(event.getButton() == MouseButton.SECONDARY) {
			AmethystApp.stage.setScene(AmethystApp.mainScene);
			return;
		}
		
		var username = usernameField.getText();
		var password = passwordField.getText();
		
		User user = null;
		try {
			var salt = Opaline.auth.getSalt(username);
			var hashedPassword = UserUtil.hashPassword(password, salt);
			user = Opaline.auth.signin(new LoginToken(username, hashedPassword));
		} catch(Exception e) {
			Log.info("Amethyst.login form error : " + e);
		}
		
		if(user != null) {
			AmethystApp.stage.setScene(AmethystApp.mainScene);
		} else {
			// show tooltip for wrong username/password combination
			if(!tip.isShowing()) {
				tip.show(loginBtn.getScene().getWindow());
				Executors.newScheduledThreadPool(1).schedule(() -> Platform.runLater(() -> tip.hide())
				, 5000, TimeUnit.MILLISECONDS);
			}
			tip.setAnchorX(event.getScreenX());
			tip.setAnchorY(event.getScreenY());
		}
	}
	
	@FXML
	public void initialize() {
		assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'loginForm.fxml'.";
		assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'loginForm.fxml'.";
		assert loginBtn != null : "fx:id=\"loginBtn\" was not injected: check your FXML file 'loginForm.fxml'.";
		assert rememberMeChk != null : "fx:id=\"rememberMeChk\" was not injected: check your FXML file 'loginForm.fxml'.";

		tip.setStyle("-fx-text-fill: red; -fx-font-size: 15px;");
	}
	
}
