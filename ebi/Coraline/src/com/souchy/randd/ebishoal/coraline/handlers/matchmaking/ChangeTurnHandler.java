package com.souchy.randd.ebishoal.coraline.handlers.matchmaking;

import com.souchy.randd.commons.coral.draft.ChangeTurn;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.ebishoal.coraline.Coraline;

import io.netty.channel.ChannelHandlerContext;

public class ChangeTurnHandler implements BBMessageHandler<ChangeTurn> {

	@Override
	public Class<ChangeTurn> getMessageClass() {
		return ChangeTurn.class;
	}

	@Override
	public void handle(ChannelHandlerContext client, ChangeTurn message) {
//		Coraline.core.bus.post(message);
	}
	
}
