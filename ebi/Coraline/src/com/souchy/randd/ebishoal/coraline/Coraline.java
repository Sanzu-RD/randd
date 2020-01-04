package com.souchy.randd.ebishoal.coraline;

import com.souchy.randd.ebishoal.commons.EbiShoalCore;
import com.souchy.randd.ebishoal.commons.EbiShoalTCP;

/**
 * Coraline is the match making client
 * 
 * @author Blank
 * @date 25 déc. 2019
 */
public class Coraline extends EbiShoalTCP { 
	
	public Coraline(String ip, int port, EbiShoalCore core) throws Exception {
		super(ip, port, core);
	}


	// extends EbiShoalCore {
	
	/*
	public final EbiShoalTCP client;
	
	public Coraline(String[] args) throws Exception {
		super(args);
		String ip = "localhost";
		int port = 7000;
		if(args.length > 0) ip = args[0];
		if(args.length > 1) port = Integer.parseInt(args[0]);
		client = new EbiShoalTCP(ip, port, this);
		//client.block(); // not here
	}

	@Override
	public String[] getRootPackages() {
		return new String[] { "com.souchy.randd.ebishoal.coraline" };
	}
	*/
	
	/**
	 * 
	 * @param username
	 * @param hashedpassword - Get this from IAuthentication.hashPassword(password, Opaline.getSalt(username))
	 */
	public static void auth(String username, String hashedpassword) {
		
	}
	
	
	public static String getSalt(String username) {
		return "";
	}

	public void enqueue() {
		
	}
	public void updateTeam() {
		
	}
	public void matchFoundAnswer() {
		
	}
	
}
