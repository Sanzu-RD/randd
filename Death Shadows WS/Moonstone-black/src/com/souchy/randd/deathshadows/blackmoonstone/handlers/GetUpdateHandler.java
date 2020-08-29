package com.souchy.randd.deathshadows.blackmoonstone.handlers;

import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.moonstone.commons.packets.c2s.GetUpdate;
import com.souchy.randd.moonstone.commons.packets.s2c.FullUpdate;
import com.souchy.randd.moonstone.commons.packets.s2c.TurnStart;

import io.netty.channel.ChannelHandlerContext;

public class GetUpdateHandler implements BBMessageHandler<GetUpdate> {

	@Override
	public Class<GetUpdate> getMessageClass() {
		return GetUpdate.class;
	}

	@Override
	public void handle(ChannelHandlerContext client, GetUpdate message) {
		var fight = client.channel().attr(Fight.attrkey).get();
		client.writeAndFlush(new FullUpdate(fight));
		client.writeAndFlush(new TurnStart(fight.timeline.turn(), fight.timeline.index(), fight.time));
	}
	
}
