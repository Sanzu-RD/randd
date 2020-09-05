package com.souchy.randd.commons.opal;

import com.souchy.randd.jade.meta.User;

public interface IAuthentication {
	
	public static class LoginToken {
		public String username;
		public String hashedPassword;
		public LoginToken() { }
		public LoginToken(String username, String hashedPassword) {
			this.username = username; this.hashedPassword = hashedPassword;
		}
		@Override
		public String toString() {
			return String.format("{%s, %s}", username, hashedPassword);
		}
	}
	
	public static class RegistrationToken {
		public String pseudo;
		public String username;
		public String password;
		public String salt;
		public String email;
		public RegistrationToken() { }
		public RegistrationToken(String pseudo, String username, String hashedPassword, String salt, String email) {
			this.pseudo = pseudo; this.username = username; this.password = hashedPassword; this.salt = salt; this.email = email;
		}
		@Override
		public String toString() {
			return String.format("{%s, %s, %s, %s, %s}", pseudo, username, password, salt, email);
		}
	}
	
	
	public User signin(String token); //LoginToken token); //String username, String password);
	
	public User signup(RegistrationToken token); //String pseudo, String username, String password, String salt, String email);

	public String getSalt(String username);
	
	public void forgotAccountFromEmail(String email);
	
	public void forgotAccountFromPhone(String phoneNumber);
	
}
