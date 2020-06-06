package com.souchy.randd.deathshadows.nodes.moonstone.black.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.moonstone.commons.packets.c2s.JoinFight;
import com.souchy.randd.moonstone.commons.packets.s2c.JoinFightResponse;

import io.netty.channel.ChannelHandlerContext;

public class JoinFightHandler implements BBMessageHandler<JoinFight> {

	@Override
	public void handle(ChannelHandlerContext client, JoinFight message) {
		// check if account id is indeed in the fight specified by fight id
		Log.info("Moonstone Black handle JoinFight");
		client.writeAndFlush(new JoinFightResponse(true));
	}

	@Override
	public Class<JoinFight> getMessageClass() {
		return JoinFight.class;
	}
	
}
