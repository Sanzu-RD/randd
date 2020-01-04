package com.souchy.randd.ebishoal.coraline;

import com.souchy.randd.commons.coral.out.MatchFound;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;

import io.netty.channel.ChannelHandlerContext;

public class MatchFoundHandler implements BBMessageHandler<MatchFound> {

	@Override
	public void handle(ChannelHandlerContext client, MatchFound message) {
		// go to champ select screen
	}

	@Override
	public Class<MatchFound> getMessageClass() {
		// TODO Auto-generated method stub
		return MatchFound.class;
	}
	
}
