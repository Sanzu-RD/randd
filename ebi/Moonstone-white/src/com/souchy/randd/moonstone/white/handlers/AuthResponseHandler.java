package com.souchy.randd.moonstone.white.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.moonstone.commons.packets.s2c.AuthResponse;
import com.souchy.randd.moonstone.commons.packets.s2c.Update;

import io.netty.channel.ChannelHandlerContext;

public class AuthResponseHandler implements BBMessageHandler<AuthResponse> {

	@Override
	public void handle(ChannelHandlerContext client, AuthResponse message) {
		// 
	}

	@Override
	public Class<AuthResponse> getMessageClass() {
		return AuthResponse.class;
	}
	
}
