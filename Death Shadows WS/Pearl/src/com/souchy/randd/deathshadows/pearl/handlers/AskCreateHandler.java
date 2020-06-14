package com.souchy.randd.deathshadows.pearl.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.AskCreate;
import com.souchy.randd.deathshadows.pearl.main.Pearl;

import io.netty.channel.ChannelHandlerContext;

public class AskCreateHandler implements BBMessageHandler<AskCreate> {

	@Override
	public void handle(ChannelHandlerContext client, AskCreate message) {
		Log.info("AskCreateHandler handling message [" + message.askedCoreType + "]");
		
		message.node = Pearl.core.create(message.askedCoreType);
		
		client.channel().writeAndFlush(message);
	}

	@Override
	public Class<AskCreate> getMessageClass() {
		return AskCreate.class;
	}

}
