package com.souchy.randd.deathshadows.blackmoonstone.riverhandlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.blackmoonstone.main.BlackMoonstone;
import com.souchy.randd.deathshadows.nodes.pearl.messaging.moonstone.CreateFight;

import gamemechanics.models.Fight;
import io.netty.channel.ChannelHandlerContext;

public class CreateFightHandler implements BBMessageHandler<CreateFight> {

	@Override
	public void handle(ChannelHandlerContext client, CreateFight message) {
		Log.info("Moonstone Black handle CreateFight");
		
		if(true) { // if the client is Coral matchmaking server or Pearl then the request is authorized
			var fight = new Fight();
			BlackMoonstone.moon.fights.put(fight.id, fight);
			// 
			message.fight = fight;
		}
		
		// return le packet au client (coral ou pearl)
		// 
//		client.writeAndFlush(message);
	}

	@Override
	public Class<CreateFight> getMessageClass() {
		return CreateFight.class;
	}
	
}
