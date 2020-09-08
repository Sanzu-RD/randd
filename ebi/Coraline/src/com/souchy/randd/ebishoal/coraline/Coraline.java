package com.souchy.randd.ebishoal.coraline;

import com.souchy.randd.commons.deathebi.msg.GetSalt;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.EbiShoalCore;
import com.souchy.randd.ebishoal.commons.EbiShoalTCP;
import com.souchy.randd.jade.matchmaking.GameQueue;
import com.souchy.randd.jade.matchmaking.Lobby;

/**
 * Coraline is the match making client
 * 
 * @author Blank
 * @date 25 déc. 2019
 */
public class Coraline { 
	
	/**
	 * Core is Amethyst set by itself on launch
	 */
	public static EbiShoalCore core;
	/**
	 * User credentials set by Amethyst on signin { username, clearpassword } <p>
	 * <b>Could be a token instead since we get it from Opaline and we'd skip the whole salt part</b>
	 */
	public static String[] credentials = new String[2];
	/**
	 * Internal Coraline tcp client
	 */
	public static EbiShoalTCP tcp;
	
	/**
	 * Current match lobby
	 */
	public static Lobby lobby;

	
	/**
	 * Connects, auths and enqueue automatically to a matchmaking server
	 */
	public static void enqueue(GameQueue queue) {
		Log.info("Coraline . enqueue");
		try {
			// start by dequeueing
			dequeue();
			tcp = new EbiShoalTCP("127.0.0.1", 7000 + queue.ordinal(), core);
			
			// auth
			var username = credentials[0];
			write(new GetSalt(username));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Disconnects from a matchmaking server, therefore dequeueing
	 */
	public static void dequeue() {
		Log.info("Coraline . dequeue");
		if(tcp != null) {
			tcp.close();
			tcp = null;
		}
		lobby = null;
	}
	
	public static void write(BBMessage msg) {
		if(tcp != null)
			tcp.write(msg);
	}
	
	
}
