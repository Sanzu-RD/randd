package com.souchy.randd.tools.rainbow.handlers;

import com.souchy.randd.commons.deathebi.msg.GetUser;
import com.souchy.randd.commons.deathebi.msg.SendSalt;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.rainbow.main.RainbowApp;

import io.netty.channel.ChannelHandlerContext;

public class SendSaltHandler implements BBMessageHandler<SendSalt> {

	@Override
	public Class<SendSalt> getMessageClass() {
		return SendSalt.class;
	}

	@Override
	public void handle(ChannelHandlerContext client, SendSalt message) {
		var username = RainbowApp.loginController.usernameField.getText();
		var password = RainbowApp.loginController.passwordField.getText();
		Log.info("Rainbow handle sendsalt");
		client.channel().writeAndFlush(new GetUser(username, password));
	}
	
}
