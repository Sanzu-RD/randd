package com.souchy.randd.tools.rainbow.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.AskNodes;

import io.netty.channel.ChannelHandlerContext;

public class NodeInfoHandler implements BBMessageHandler<AskNodes> {

	@Override
	public Class<AskNodes> getMessageClass() {
		return AskNodes.class;
	}

	@Override
	public void handle(ChannelHandlerContext client, AskNodes message) {
		
	}
	
}
