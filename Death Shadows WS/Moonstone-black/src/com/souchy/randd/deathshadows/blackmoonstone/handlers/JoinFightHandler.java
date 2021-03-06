package com.souchy.randd.deathshadows.blackmoonstone.handlers;

import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.jade.Constants;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.blackmoonstone.main.BlackMoonstone;
import com.souchy.randd.deathshadows.blackmoonstone.main.FightChannelSystem;
import com.souchy.randd.jade.meta.User;
import com.souchy.randd.moonstone.commons.packets.c2s.JoinFight;
import com.souchy.randd.moonstone.commons.packets.s2c.JoinFightResponse;
import com.souchy.randd.moonstone.commons.packets.s2c.TurnStart;

import io.netty.channel.ChannelHandlerContext;

public class JoinFightHandler implements BBMessageHandler<JoinFight> {

	@Override
	public void handle(ChannelHandlerContext client, JoinFight message) {
		// check if account id is indeed in the fight specified by fight id
		
		var user = client.channel().attr(User.attrkey).get();
		var fight = BlackMoonstone.moon.fights.get(message.fightID);

//		Log.info("BlackMoonstone fights " + BlackMoonstone.moon.fights);
//		Log.info("Moonstone Black handle JoinFight ["+message.fightID+"] " + fight + " from " + user);
		
		var accepted = user != null && fight != null;
		
		client.writeAndFlush(new JoinFightResponse(accepted));
		
		if(accepted) {
			client.channel().attr(Fight.attrkey).set(fight);
			fight.get(FightChannelSystem.class).add(client.channel());
			
//			Log.format("join fight syst size %s", fight.get(FightChannelSystem.class).size());
			
//			Fight fight = e.ctx.channel().attr(Fight.attrkey).get();
//			var user = e.ctx.channel().attr(User.attrkey).get();
//			fight.creatures.foreach(c -> c.add(user._id)); // FIXME pour debug
			
//			client.writeAndFlush(new FullUpdate(fight)); // wait till sapphiregame has loaded its assets to ask for a fullupdate
		}
	}

	@Override
	public Class<JoinFight> getMessageClass() {
		return JoinFight.class;
	}
	
}
