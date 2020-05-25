package com.souchy.randd.moonstone.white.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.moonstone.commons.packets.s2c.Update;

import io.netty.channel.ChannelHandlerContext;

public class UpdateHandler implements BBMessageHandler<Update> {

	@Override
	public void handle(ChannelHandlerContext client, Update message) {
		// set Fight object and update renderer/UIs
	}

	@Override
	public Class<Update> getMessageClass() {
		return Update.class;
	}
	
}
