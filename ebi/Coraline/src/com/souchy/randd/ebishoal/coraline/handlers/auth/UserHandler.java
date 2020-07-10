package com.souchy.randd.ebishoal.coraline.handlers.auth;

import com.souchy.randd.commons.deathebi.msg.SendUser;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;

import io.netty.channel.ChannelHandlerContext;

public class UserHandler implements BBMessageHandler<SendUser> {

	@Override
	public Class<SendUser> getMessageClass() {
		return SendUser.class;
	}

	@Override
	public void handle(ChannelHandlerContext client, SendUser message) {
		
	}
	
}
