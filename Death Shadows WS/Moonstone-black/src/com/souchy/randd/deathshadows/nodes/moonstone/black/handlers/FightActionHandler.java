package com.souchy.randd.deathshadows.nodes.moonstone.black.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.moonstone.commons.packets.c2s.FightAction;

public class FightActionHandler implements BBMessageHandler<FightAction> {

	@Override
	public void handle(FightAction message) {
		System.out.println("FightActionHandler handling message [" + message + "]");
	}

	@Override
	public Class<FightAction> getMessageClass() {
		return FightAction.class;
	}

}
