package com.souchy.randd.deathshadows.nodes.moonstone.black.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.moonstone.commons.packets.c2s.Auth;

import io.netty.channel.ChannelHandlerContext;

public class AuthHandler implements BBMessageHandler<Auth> {

	@Override
	public void handle(ChannelHandlerContext client, Auth message) {
		// check if account id is indeed in the fight specified by fight id
	}

	@Override
	public Class<Auth> getMessageClass() {
		return Auth.class;
	}
	
}
