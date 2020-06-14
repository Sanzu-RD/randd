package com.souchy.randd.deathshadows.pearl.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.SelfIdentify;

import io.netty.channel.ChannelHandlerContext;

public class SelfIdentifyHandler implements BBMessageHandler<SelfIdentify> {

	@Override
	public void handle(ChannelHandlerContext ctx, SelfIdentify message) {
		Log.info("SelfIdentifyHandler handling message [" + message + "]");
	}

	@Override
	public Class<SelfIdentify> getMessageClass() {
		return SelfIdentify.class;
	}

}
