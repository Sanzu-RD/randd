package com.souchy.randd.moonstone.white;

import com.souchy.randd.commons.deathebi.msg.GetSalt;
import com.souchy.randd.commons.deathebi.msg.GetUser;
import com.souchy.randd.ebishoal.commons.EbiShoalCore;
import com.souchy.randd.ebishoal.commons.EbiShoalTCP;

public class WhiteMoonstone extends EbiShoalTCP { 
	
	public static WhiteMoonstone moon;
	
//	public EbiShoalCore core;
	
	public WhiteMoonstone(String ip, int port, EbiShoalCore core) throws Exception {
		super(ip, port, core);
//		this.core = core;
		moon = this;
	}
	
//	public void post(Object event) {
//		core.bus.post(event);
//	}

	
	public String username;
	public String password;
	public void auth(String username, String password) {
		this.username = username;
		this.password = password;
		moon.write(new GetSalt(username));
	}
	
}
