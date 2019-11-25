package com.souchy.randd.commons.opal;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.jade.meta.User;

public interface IAuthentication {
	
	public static class LoginToken {
		public String username;
		public String password;
		public LoginToken(String username, String hashedPassword) {
			this.username = username; this.password = hashedPassword;
		}
		@Override
		public String toString() {
			return String.format("{%s, %s}", username, password);
		}
	}
	public static class RegistrationToken {
		public String pseudo;
		public String username;
		public String password;
		public String salt;
		public String email;
		public RegistrationToken(String pseudo, String username, String hashedPassword, String salt, String email) {
			this.pseudo = pseudo; this.username = username; this.password = hashedPassword; this.salt = salt; this.email = email;
		}
		@Override
		public String toString() {
			return String.format("{%s, %s, %s, %s, %s}", pseudo, username, password, salt, email);
		}
	}
	
	
	public User signin(LoginToken token); //String username, String password);
	
	public User signup(RegistrationToken token); //String pseudo, String username, String password, String salt, String email);

	public String getSalt(String username);
	
	public void forgotAccountFromEmail(String email);
	
	public void forgotAccountFromPhone(String phoneNumber);
	
	

	/**
	 * TODO : check for ASCII characters only
	 * @param pseudo
	 * @param username
	 * @param password
	 * @param email
	 * @return
	 */
	public static boolean validSignupInput(String pseudo, String username, String password, String email) {
		// pseudo 
		{
			if(pseudo.length() < 2 || pseudo.length() > 20) return false;
			
		}
		// username 
		{
			if(username.length() < 4 || username.length() > 20) return false;
			//if(username.contains("")) return false;
		}
		// password
		{
			if(password.length() < 8 || password.length() > 20) return false;
			//if(password.contains("")) return false;
			
		}
		// email
		{
			if(email.length() < 5 || email.length() > 50) return false;
			if(!email.contains("@") || !email.contains(".")) return false;
		}
		return true;
	}
	
	public static final int iterations = 65536;
	public static final int key_length = 512; // bits
	public static final int salt_length = key_length / 8; // bytes
	public static final String algorithm = "PBKDF2WithHmacSHA512";
	public static final SecureRandom secRnd = new SecureRandom();
	public static String generateSalt() {
		byte[] salt = new byte[salt_length];
		secRnd.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}
	
	public static String hashPassword(String password, String salt) {
		char[] chars = password.toCharArray();
		byte[] bytes = salt.getBytes();
		
		var spec = new PBEKeySpec(chars, bytes, iterations, key_length);
		
		Arrays.fill(chars, Character.MIN_VALUE);
		
		try {
			var fac = SecretKeyFactory.getInstance(algorithm);
			byte[] securePassword = fac.generateSecret(spec).getEncoded();
			return Base64.getEncoder().encodeToString(securePassword);
		} catch(NoSuchAlgorithmException | InvalidKeySpecException e) {
			Log.error("Opaline.Authentication.hashPassword : ", e);
			return null;
		} finally {
			spec.clearPassword();
		}
	}
}
