package com.souchy.randd.moonstone.white.handlers;

import com.souchy.randd.commons.deathebi.msg.SendUser;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.jade.meta.User;
import com.souchy.randd.moonstone.commons.packets.c2s.JoinFight;
import com.souchy.randd.moonstone.white.Moonstone;

import io.netty.channel.ChannelHandlerContext;

public class SendUserHandler implements BBMessageHandler<SendUser> {

	@Override
	public void handle(ChannelHandlerContext client, SendUser message) {
		Log.info("Moonstone White handle SendUser reception " + message.user);
		
		if(message.user == null) {
			return;
		}
		
		// set user object
		client.channel().attr(User.attrkey).set(message.user);
		
		// get fight id from args[] sent by amethyst to sapphire
		var auth = client.channel().attr(Moonstone.authKey).get();
		var fightid = auth[2];
		
		// deamande à rejoindre un combat
		var join = new JoinFight(Integer.parseInt(fightid)); 
		Moonstone.write(join);
	}

	@Override
	public Class<SendUser> getMessageClass() {
		return SendUser.class;
	}
	
}
