package com.souchy.randd.moonstone.white;


import com.souchy.randd.commons.deathebi.msg.GetSalt;
import com.souchy.randd.commons.deathebi.msg.GetUser;
import com.souchy.randd.ebishoal.commons.EbiShoalCore;
import com.souchy.randd.ebishoal.commons.EbiShoalTCP;

import io.netty.util.AttributeKey;

public class WhiteMoonstone extends EbiShoalTCP { 
	
	// attribute key for this tcpclient so it can be retrieved later from the client channel
	public static final AttributeKey<WhiteMoonstone> attrKey = AttributeKey.newInstance("moonstone.white");

	// temp data for authentication and joining a fight
	public String username;
	public String password;
	public int fightid;
	
	public WhiteMoonstone(String ip, int port, EbiShoalCore core) throws Exception {
		super(ip, port, core);
		this.channel.attr(attrKey);
	}
	
	public void auth(String username, String password, int fightid) {
		this.username = username;
		this.password = password;
		this.fightid = fightid;
		this.write(new GetSalt(username));
	}
	
}
