package com.souchy.randd.deathshadows.blackmoonstone.handlers;

import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.deathshadows.blackmoonstone.main.FightChannelSystem;
import com.souchy.randd.moonstone.commons.packets.c2s.PassTurn;

import io.netty.channel.ChannelHandlerContext;

public class PassTurnHandler implements BBMessageHandler<PassTurn> {

	@Override
	public void handle(ChannelHandlerContext client, PassTurn message) {
		// 
		Fight fight = client.channel().attr(Fight.attrkey).get();
		
		fight.get(FightChannelSystem.class).broadcast(message);
	}

	@Override
	public Class<PassTurn> getMessageClass() {
		return PassTurn.class;
	}
	
}
