package com.souchy.randd.moonstone.white.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.moonstone.commons.packets.s2c.JoinFightResponse;
import com.souchy.randd.moonstone.commons.packets.s2c.Update;

import io.netty.channel.ChannelHandlerContext;

public class JoinFightResponseHandler implements BBMessageHandler<JoinFightResponse> {

	@Override
	public void handle(ChannelHandlerContext client, JoinFightResponse message) {
		// 
		Log.info("Moonstone White handle JoinFightResponse");
	}

	@Override
	public Class<JoinFightResponse> getMessageClass() {
		return JoinFightResponse.class;
	}
	
}
