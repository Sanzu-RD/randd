package com.souchy.randd.deathshadows.nodes.pearl.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.nodes.pearl.main.Pearl;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.AskCreate;

import io.netty.channel.ChannelHandlerContext;

public class AskCreateHandler implements BBMessageHandler<AskCreate> {

	@Override
	public void handle(ChannelHandlerContext client, AskCreate message) {
		Log.info("AskCreateHandler handling message [" + message.coreName + "]");
		
		Pearl.core.create(message.coreName);
		
	}

	@Override
	public Class<AskCreate> getMessageClass() {
		return AskCreate.class;
	}

}
