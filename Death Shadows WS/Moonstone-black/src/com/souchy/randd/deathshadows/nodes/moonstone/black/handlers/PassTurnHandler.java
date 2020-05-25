package com.souchy.randd.deathshadows.nodes.moonstone.black.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.moonstone.commons.packets.c2s.PassTurn;

import io.netty.channel.ChannelHandlerContext;

public class PassTurnHandler implements BBMessageHandler<PassTurn> {

	@Override
	public void handle(ChannelHandlerContext client, PassTurn message) {
		// 
	}

	@Override
	public Class<PassTurn> getMessageClass() {
		return PassTurn.class;
	}
	
}
