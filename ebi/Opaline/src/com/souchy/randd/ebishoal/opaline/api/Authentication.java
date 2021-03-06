package com.souchy.randd.ebishoal.opaline.api;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.souchy.randd.commons.opal.IAuthentication;
import com.souchy.randd.commons.opal.OpalCommons;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.jade.meta.User;

public class Authentication implements IAuthentication {

	private static final String path = OpalCommons.authentication;
	private static final WebTarget target = Opaline.target(path);

	
	@Override
	public User signup(RegistrationToken token) { 
		Log.info("Opaline.Authentication.signup : token = " + token);
		var user = Opaline.post(target.path("signup"), Entity.entity(token, MediaType.APPLICATION_JSON), User.class, MediaType.APPLICATION_JSON_TYPE);
		Log.info("Opaline.Authentication.signup : " + user);
		return user;
	}
	@Override
	public User signin(String token) { // LoginToken token) { 
		Log.info("Opaline.Authentication.signin : token = " + token);
//		var user = Opaline.post(target.path("signin"), Entity.entity(token, MediaType.APPLICATION_JSON), User.class, MediaType.APPLICATION_JSON_TYPE);
		var user = Opaline.get(target.path("signin/" + token), User.class);
		Log.info("Opaline.Authentication.signin : " + user + ", " + user._id.getCounter());
		return user;
	}
	
	@Override
	public String getSalt(String username) {
		Log.info("Opaline.Authentication.getSalt : username = " + username);
		var salt = Opaline.get(target.path("salt/" + username), String.class);
		Log.info("Opaline.Authentication.getSalt : " + salt);
		return salt;
	}
	@Override
	public void forgotAccountFromEmail(String email) {
		
	}
	@Override
	public void forgotAccountFromPhone(String phoneNumber) {
		
	}
	
	
}
