package com.souchy.randd.deathshadows.pearl.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.AskNodes;
import com.souchy.randd.deathshadows.pearl.main.Pearl;

import io.netty.channel.ChannelHandlerContext;

public class AskNodesHandler implements BBMessageHandler<AskNodes> {

	@Override
	public Class<AskNodes> getMessageClass() {
		return AskNodes.class;
	}

	@Override
	public void handle(ChannelHandlerContext client, AskNodes message) {
		Log.info("Pearl AskNodesHandler");
		// just set the nodes and send the message back
		message.nodes = Pearl.core.nodes;
		client.channel().writeAndFlush(message);
	}
	
}
