package com.souchy.randd.deathshadows.coral.handlers;

import com.souchy.randd.commons.coral.in.MatchFoundAnswer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;

import io.netty.channel.ChannelHandlerContext;

public class MatchFoundAnswerHandler implements BBMessageHandler<MatchFoundAnswer> {

	@Override
	public void handle(ChannelHandlerContext ctx, MatchFoundAnswer message) {
		
	}

	@Override
	public Class<MatchFoundAnswer> getMessageClass() {
		return MatchFoundAnswer.class;
	}
	
}
