package com.souchy.randd.deathshadows.blackmoonstone.handlers;

import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.deathshadows.blackmoonstone.main.FightClientSystem;
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
		Fight fight = client.channel().attr(Fight.attrkey).get();
		
		fight.get(FightClientSystem.class).foreach(c -> {
			// si c'est un message privé envoyé à quelqu'un en particulier
			if (message.channel.startsWith("user")) {
				var usertarget = message.channel.split(".")[1];
				if (usertarget == c.attr(User.attrkey).get().pseudo) {
					c.writeAndFlush(message);
				}
			} else {
				// si c'est un message envoyé au channel du fight (si on jouait en équipe, on pourrait avoir les channels : all, équipe, spectateur)
				c.writeAndFlush(message);
			}
		});
	}

}
