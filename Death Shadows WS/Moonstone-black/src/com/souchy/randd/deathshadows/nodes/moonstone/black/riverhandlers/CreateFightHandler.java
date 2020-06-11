package com.souchy.randd.deathshadows.nodes.moonstone.black.riverhandlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.nodes.moonstone.black.BlackMoonstone;
import com.souchy.randd.moonstone.commons.packets.CreateFight;

import gamemechanics.models.Fight;
import io.netty.channel.ChannelHandlerContext;

public class CreateFightHandler implements BBMessageHandler<CreateFight> {

	@Override
	public void handle(ChannelHandlerContext client, CreateFight message) {
		// check if account id is indeed in the fight specified by fight id
		Log.info("Moonstone Black handle CreateFight");
//		client.writeAndFlush(new JoinFightResponse(true));
		
		int id = 0;
		if(true) { // if the client is coral matchmaking server, then the request is authorized
			var fight = new Fight();
			id = fight.id;
			BlackMoonstone.moon.fights.add(fight);
		}
		// reply so the client knows if the request was authorized (0 means not) and what's the fight ID so coral can tell the players what fight to join
//		client.writeAndFlush(new CreateFightResponse(id));
	}

	@Override
	public Class<CreateFight> getMessageClass() {
		return CreateFight.class;
	}
	
}
