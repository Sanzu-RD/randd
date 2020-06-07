package com.souchy.randd.moonstone.white.handlers;

import com.souchy.randd.commons.deathebi.UserUtil;
import com.souchy.randd.commons.deathebi.msg.GetUser;
import com.souchy.randd.commons.deathebi.msg.SendSalt;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.moonstone.white.WhiteMoonstone;

import io.netty.channel.ChannelHandlerContext;

public class SendSaltHandler implements BBMessageHandler<SendSalt> {

	@Override
	public void handle(ChannelHandlerContext client, SendSalt message) {
		// 
		Log.info("Moonstone White handle SendSalt reception");
		
		var moon = client.channel().attr(WhiteMoonstone.attrKey).get();
		
		var getuser = new GetUser(moon.username, UserUtil.hashPassword(moon.password, message.salt));
		moon.write(getuser);
	}

	@Override
	public Class<SendSalt> getMessageClass() {
		return SendSalt.class;
	}
	
}