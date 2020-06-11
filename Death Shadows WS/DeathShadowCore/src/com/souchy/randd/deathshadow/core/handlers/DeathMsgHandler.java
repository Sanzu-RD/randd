package com.souchy.randd.deathshadow.core.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.multihandlers.BBMessageHandlers;
import com.souchy.randd.commons.net.netty.server.NettyHandler;
import com.souchy.randd.jade.meta.User;

import io.netty.channel.ChannelHandlerContext;

public class DeathMsgHandler extends NettyHandler {
	
	public AuthenticationFilter filter = new AuthenticationFilter();

	public DeathMsgHandler(BBMessageHandlers msgHandlers) {
		super(msgHandlers, new AuthenticationFilter());
		
	}

}
