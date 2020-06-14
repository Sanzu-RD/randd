package com.souchy.randd.deathshadows.pearl.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.AskAvailable;

import io.netty.channel.ChannelHandlerContext;

public class AskAvailableHandler implements BBMessageHandler<AskAvailable> {

	@Override
	public void handle(ChannelHandlerContext ctx, AskAvailable message) {
		Log.info("AskAvailableHandler handling message [" + message + "]");
		
		
	}

	@Override
	public Class<AskAvailable> getMessageClass() {
		return AskAvailable.class;
	}

}
