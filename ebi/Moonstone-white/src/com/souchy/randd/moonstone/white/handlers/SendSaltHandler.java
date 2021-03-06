package com.souchy.randd.moonstone.white.handlers;

import com.souchy.randd.commons.deathebi.UserUtil;
import com.souchy.randd.commons.deathebi.msg.GetUser;
import com.souchy.randd.commons.deathebi.msg.SendSalt;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.moonstone.white.Moonstone;

import io.netty.channel.ChannelHandlerContext;

public class SendSaltHandler implements BBMessageHandler<SendSalt> {

	@Override
	public void handle(ChannelHandlerContext client, SendSalt message) {
		// 
		Log.info("Moonstone White handle SendSalt reception");
		
		var authCredentials = client.channel().attr(Moonstone.authKey).get();
		
		var username = authCredentials[0];
		var password = authCredentials[1];
		var hashedPassword = UserUtil.hashPassword(password, message.salt);
		
//		Log.info("hashed pass " + hashedPassword);
		
		var getuser = new GetUser(username, hashedPassword);
		Moonstone.writes(getuser);
	}

	@Override
	public Class<SendSalt> getMessageClass() {
		return SendSalt.class;
	}
	
}