package com.souchy.randd.moonstone.white;


import com.souchy.randd.commons.deathebi.msg.GetSalt;
import com.souchy.randd.ebishoal.commons.EbiShoalCore;
import com.souchy.randd.ebishoal.commons.EbiShoalTCP;

import gamemechanics.models.Fight;
import io.netty.util.AttributeKey;

public class Moonstone extends EbiShoalTCP { 
	
	public static final AttributeKey<String[]> authKey = AttributeKey.newInstance("moonstone.white.auth");
	
	public static Moonstone moon;
	public static Fight fight;
	
	
	public Moonstone(String ip, int port, EbiShoalCore core) throws Exception {
		super(ip, port, core);
		moon = this;
//		fight = new Fight();
	}
	
	public void auth(String username, String password, int fightid) {
//		this.channel.attr(authKey).set(new String[]{ username, password, fightid + "" });
		write(new GetSalt(username));
	}
	
//	public static void write(BBMessage msg) {
//		moon.channel.writeAndFlush(msg);
//	}
	
	// add fight object to here as fieldmember or ecs component ? 
	// but the fight object is already an engine
	// maybe just put the fight here and sapphire will use it instead of putting it in sapphire and having white using it
	// makes more sense
	// why not make it static ?
	
}
