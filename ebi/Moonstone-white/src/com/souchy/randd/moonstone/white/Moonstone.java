package com.souchy.randd.moonstone.white;


import com.souchy.randd.commons.deathebi.msg.GetSalt;
import com.souchy.randd.commons.deathebi.msg.GetUser;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.ebishoal.commons.EbiShoalCore;
import com.souchy.randd.ebishoal.commons.EbiShoalTCP;

import gamemechanics.models.Fight;
import io.netty.util.AttributeKey;

public class Moonstone extends EbiShoalTCP { 
	
	// attribute key for this tcpclient so it can be retrieved later from the client channel
//	public static final AttributeKey<Moonstone> moonKey = AttributeKey.newInstance("moonstone.white");
	public static final AttributeKey<String[]> authKey = AttributeKey.newInstance("moonstone.white.auth");
	
	public static Moonstone moon;
	public static Fight fight;
	
	// temp data for authentication and joining a fight
//	public String username;
//	public String password;
//	public int fightid;
	
	
	public Moonstone(String ip, int port, EbiShoalCore core) throws Exception {
		super(ip, port, core);
		moon = this;
//		this.channel.attr(moonKey).set(this);
	}
	
	public void auth(String username, String password, int fightid) {
//		this.username = username;
//		this.password = password;
//		this.fightid = fightid;
		this.channel.attr(authKey).set(new String[]{ username, password, fightid + "" });
		write(new GetSalt(username));
	}
	
	public static void write(BBMessage msg) {
		moon.channel.writeAndFlush(msg);
	}
	
	// add fight object to here as fieldmember or ecs component ? 
	// but the fight object is already an engine
	// maybe just put the fight here and sapphire will use it instead of putting it in sapphire and having white using it
	// makes more sense
	// why not make it static ?
	
}
