package com.souchy.randd.deathshadows.nodes.moonstone.black.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.nodes.moonstone.black.BlackMoonstone;
import com.souchy.randd.jade.meta.User;
import com.souchy.randd.moonstone.commons.packets.c2s.JoinFight;
import com.souchy.randd.moonstone.commons.packets.s2c.FullUpdate;
import com.souchy.randd.moonstone.commons.packets.s2c.JoinFightResponse;

import gamemechanics.models.Fight;
import io.netty.channel.ChannelHandlerContext;

public class JoinFightHandler implements BBMessageHandler<JoinFight> {

	@Override
	public void handle(ChannelHandlerContext client, JoinFight message) {
		// check if account id is indeed in the fight specified by fight id
		
		var user = client.channel().attr(User.attrkey).get();
		var fight = BlackMoonstone.moon.fights.get(message.fightID);

		Log.info("BlackMoonstone fights " + BlackMoonstone.moon.fights);
		Log.info("Moonstone Black handle JoinFight ["+message.fightID+"] " + fight + " from " + user);
		

		var accepted = user != null && fight != null;
		client.writeAndFlush(new JoinFightResponse(accepted));
		if(accepted)
			client.writeAndFlush(new FullUpdate(fight));
	}

	@Override
	public Class<JoinFight> getMessageClass() {
		return JoinFight.class;
	}
	
}
