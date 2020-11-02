package com.souchy.randd.moonstone.white.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.moonstone.commons.packets.c2s.GetUpdate;
import com.souchy.randd.moonstone.commons.packets.s2c.JoinFightResponse;
import com.souchy.randd.moonstone.white.Moonstone;

import io.netty.channel.ChannelHandlerContext;

public class JoinFightResponseHandler implements BBMessageHandler<JoinFightResponse> {

	@Override
	public void handle(ChannelHandlerContext client, JoinFightResponse message) {
		// 
		Log.info("Moonstone White handle JoinFightResponse accepted=" + message.accepted);
		
		// close sapphire if not accepted
		if(!message.accepted) {
			System.exit(0);
		}
		else {
			Moonstone.writes(new GetUpdate());
		}
	}

	@Override
	public Class<JoinFightResponse> getMessageClass() {
		return JoinFightResponse.class;
	}
	
}
