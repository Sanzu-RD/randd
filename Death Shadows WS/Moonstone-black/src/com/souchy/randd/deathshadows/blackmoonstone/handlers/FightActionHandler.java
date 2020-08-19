package com.souchy.randd.deathshadows.blackmoonstone.handlers;

import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.blackmoonstone.main.BlackMoonstone;
import com.souchy.randd.deathshadows.blackmoonstone.main.FightClientSystem;
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
		Fight fight = client.channel().attr(Fight.attrkey).get();
		
		fight.get(FightClientSystem.class).broadcast(message);
	}

}
