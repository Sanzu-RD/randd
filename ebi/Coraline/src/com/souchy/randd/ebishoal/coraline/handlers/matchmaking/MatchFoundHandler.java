package com.souchy.randd.ebishoal.coraline.handlers.matchmaking;

import com.souchy.randd.commons.coral.out.MatchFound;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.coraline.Coraline;

import io.netty.channel.ChannelHandlerContext;

public class MatchFoundHandler implements BBMessageHandler<MatchFound> {

	@Override
	public void handle(ChannelHandlerContext client, MatchFound message) {
		Log.info("Coraline match found handler");
		// go to champ select screen
//		Coraline.core.bus.post(message);
		Coraline.lobby = message.lobby;
	}

	@Override
	public Class<MatchFound> getMessageClass() {
		return MatchFound.class;
	}
	
}
