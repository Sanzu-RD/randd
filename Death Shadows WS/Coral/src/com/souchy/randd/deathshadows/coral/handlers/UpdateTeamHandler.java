package com.souchy.randd.deathshadows.coral.handlers;

import com.souchy.randd.commons.coral.in.UpdateTeam;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;

import io.netty.channel.ChannelHandlerContext;

public class UpdateTeamHandler implements BBMessageHandler<UpdateTeam> {

	@Override
	public void handle(ChannelHandlerContext client, UpdateTeam message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<UpdateTeam> getMessageClass() {
		return UpdateTeam.class;
	}
	
}
