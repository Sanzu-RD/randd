package com.souchy.randd.deathshadows.blackmoonstone.handlers;

import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.deathshadows.blackmoonstone.main.FightChannelSystem;
import com.souchy.randd.jade.meta.User;
import com.souchy.randd.moonstone.commons.packets.ICM;

import io.netty.channel.ChannelHandlerContext;

/**
 * Instant chat message handler
 * @author Robyn Girardeau
 * @date 18 août 2020
 */
public class ICMHandler implements BBMessageHandler<ICM> {

	@Override
	public Class<ICM> getMessageClass() {
		return ICM.class;
	}
	
	@Override
	public void handle(ChannelHandlerContext client, ICM message) {
		var user = client.channel().attr(User.attrkey).get();
		var fight = client.channel().attr(Fight.attrkey).get();
		var syst = fight.get(FightChannelSystem.class);
		
		message.author = user.pseudo;
		
		// si c'est un message privé envoyé à quelqu'un en particulier
		if(message.channel.startsWith("user")) {
			var usertarget = message.channel.split(".")[1];
			var channeltarget = syst.first(c -> usertarget == c.attr(User.attrkey).get().pseudo);
			if(channeltarget != null) { // usertarget == c.attr(User.attrkey).get().pseudo) {
				channeltarget.writeAndFlush(message);
			}
		}
		// si c'est un message envoyé au channel du fight (si on jouait en équipe, on pourrait avoir les channels : all, équipe, spectateur)
		else {
			syst.foreach(c -> c.writeAndFlush(message));
		}
		
	}

}
