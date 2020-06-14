package com.souchy.randd.tools.rainbow.handlers;

import com.souchy.randd.commons.deathebi.msg.SendUser;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.tools.rainbow.main.RainbowApp;

import io.netty.channel.ChannelHandlerContext;
import javafx.application.Platform;

public class SendUserHandler implements BBMessageHandler<SendUser> {

	@Override
	public Class<SendUser> getMessageClass() {
		return SendUser.class;
	}

	@Override
	public void handle(ChannelHandlerContext client, SendUser message) {
		Log.info("Rainbow handle senduser");
		Platform.runLater(() -> RainbowApp.stage.setScene(RainbowApp.mainScene));
	}
	
}
