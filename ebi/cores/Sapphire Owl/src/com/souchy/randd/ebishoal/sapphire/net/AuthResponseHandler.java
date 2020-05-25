package com.souchy.randd.ebishoal.sapphire.net;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.ebishoal.sapphire.main.SapphireOwl;
import com.souchy.randd.moonstone.commons.packets.s2c.AuthResponse;
import com.souchy.randd.moonstone.commons.packets.s2c.Update;

import io.netty.channel.ChannelHandlerContext;

public class AuthResponseHandler implements BBMessageHandler<AuthResponse> {

	@Override
	public void handle(ChannelHandlerContext client, AuthResponse message) {
		// 
		
		if(message.accepted) {
			SapphireOwl.core.start();
		}
	}

	@Override
	public Class<AuthResponse> getMessageClass() {
		return AuthResponse.class;
	}
	
}
