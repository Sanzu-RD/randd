package com.souchy.randd.ebishoal.coraline.handlers.matchmaking;

import com.souchy.randd.commons.coral.draft.SelectCreature;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.coraline.Coraline;

import io.netty.channel.ChannelHandlerContext;

public class SelectedCreatureHandler implements BBMessageHandler<SelectCreature> {

	@Override
	public Class<SelectCreature> getMessageClass() {
		return SelectCreature.class;
	}

	@Override
	public void handle(ChannelHandlerContext client, SelectCreature message) {
		Log.info("Coraline select creature handler");
//		Coraline.core.bus.post(message);
	}
	
}
