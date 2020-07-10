package com.souchy.randd.deathshadows.coral.handlers;

import com.souchy.randd.commons.coral.draft.SelectCreature;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.deathshadows.coral.main.Coral;
import com.souchy.randd.jade.matchmaking.GameQueue;
import com.souchy.randd.jade.matchmaking.Lobby;
import com.souchy.randd.jade.meta.User;

import io.netty.channel.ChannelHandlerContext;

public class SelectedCreatureHandler implements BBMessageHandler<SelectCreature> {

	@Override
	public Class<SelectCreature> getMessageClass() {
		return SelectCreature.class;
	}

	@Override
	public void handle(ChannelHandlerContext client, SelectCreature message) {
		try {
			User user = client.channel().attr(User.attrkey).get();
			Lobby lobby = client.channel().attr(Lobby.attrkey).get();
			
			// fix team unless it's a mocking lobby
			if(lobby.type != GameQueue.mock) 
				message.team = lobby.teams.get(user._id);
			
			// broadcast select to all lobby participants
			for (var id : lobby.teams.keySet()) {
				Coral.coral.server.users.get(id).writeAndFlush(message);
			}
		} catch (Exception e) {
			client.channel().close();
		}
	}
	
}
