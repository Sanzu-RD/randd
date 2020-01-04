package com.souchy.randd.deathshadows.coral;

import java.util.HashMap;
import java.util.Map;

import com.souchy.randd.commons.deathebi.Microservice;
import com.souchy.randd.commons.net.netty.NettyBuilder;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.server.NettyServer;
import com.souchy.randd.jade.meta.User;

import io.netty.channel.Channel;

public class CoralService extends Microservice {
	
	private final int port;
	
	public Map<User, Channel> users;
	
	CoralService(int port) { 
		this.port = port;
		this.users = new HashMap<>();
	}
	
	@Override 
	protected void preinit() {
	}

	@Override
	protected Channel initNetty(NettyBuilder builder) throws Exception {
		
		/*
			NettyServer
				servchannel
				list<clientchannels> channels with .attr(userkey)
				
				decoder/encoder
				connectionHandler
				msgHandler
				
				authentication handler (getSalt(username), sendSalt(salt), auth(user,hashedPass), sendUser(user))
				
				map<user, channel>
			
		 */
		
		NettyServer.create().port(port).SSL(false).encoder(null).decoder(null).handler(new CoralHandler());
		return builder.tcp(port, msgFactories, msgHandlers);
	}

	@Override
	protected String[] getRootPackages() {
		return new String[]{ "com.souchy.randd.deathshadows.coral" };
	}
	
	public void send(User user, BBMessage m) {
		
	}
	
}