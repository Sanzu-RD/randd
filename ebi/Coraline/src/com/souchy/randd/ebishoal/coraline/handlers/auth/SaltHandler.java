package com.souchy.randd.ebishoal.coraline.handlers.auth;

import com.souchy.randd.commons.deathebi.UserUtil;
import com.souchy.randd.commons.deathebi.msg.GetUser;
import com.souchy.randd.commons.deathebi.msg.SendSalt;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.coraline.Coraline;

import io.netty.channel.ChannelHandlerContext;

public class SaltHandler implements BBMessageHandler<SendSalt> {

	@Override
	public Class<SendSalt> getMessageClass() {
		return SendSalt.class;
	}

	@Override
	public void handle(ChannelHandlerContext client, SendSalt message) {
		
		var username = Coraline.credentials[0];
		var password = Coraline.credentials[1];
		var hashedPassword = UserUtil.hashPassword(password, message.salt);
		
		Log.info("Coraline GetUser " + username + ", " + password);
		var getuser = new GetUser(username, hashedPassword);
		client.channel().writeAndFlush(getuser);
	}
	
}
