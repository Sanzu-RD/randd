package com.souchy.randd.ebishoal.coraline.handlers.matchmaking;

import com.souchy.randd.commons.coral.out.MatchLost;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.ebishoal.coraline.Coraline;

import io.netty.channel.ChannelHandlerContext;

public class MatchLostHandler implements BBMessageHandler<MatchLost> {

	@Override
	public void handle(ChannelHandlerContext client, MatchLost message) {
		// TODO Auto-generated method stub
		// return to queue
		Coraline.lobby = null;
	}

	@Override
	public Class<MatchLost> getMessageClass() {
		return MatchLost.class;
	}
	
}
