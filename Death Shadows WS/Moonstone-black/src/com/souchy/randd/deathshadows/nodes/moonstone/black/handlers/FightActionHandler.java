package com.souchy.randd.deathshadows.nodes.moonstone.black.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.moonstone.commons.packets.c2s.FightAction;

import io.netty.channel.ChannelHandlerContext;

public class FightActionHandler implements BBMessageHandler<FightAction> {

	@Override
	public Class<FightAction> getMessageClass() {
		return FightAction.class;
	}

	@Override
	public void handle(ChannelHandlerContext client, FightAction message) {
		Log.info("FightActionHandler handling message [" + message + "]");
	}

}
