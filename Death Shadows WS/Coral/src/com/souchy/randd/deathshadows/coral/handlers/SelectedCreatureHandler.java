package com.souchy.randd.deathshadows.coral.handlers;

import java.util.Arrays;

import com.souchy.randd.commons.coral.draft.SelectCreature;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.coral.main.Coral;
import com.souchy.randd.jade.matchmaking.GameQueue;
import com.souchy.randd.jade.matchmaking.Lobby;
import com.souchy.randd.jade.meta.JadeCreature;
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

			Log.format("Coral on select creature (%s)", message.modelid);
			
			// if the creature model doesnt exist, cancel
			if(!DiamondModels.creatures.containsKey(message.modelid)) {
				return;
			}
			
			User user = client.channel().attr(User.attrkey).get();
			Lobby lobby = client.channel().attr(Lobby.attrkey).get();

			Log.format("Coral on select creature (%s), user %s, lobby %s", message.modelid, user, lobby);
			
			// fix team unless it's a mocking lobby
			if(lobby.type != GameQueue.mock) 
				message.team = lobby.teams.get(user._id);
			
			// add new jade creature
			var crea = new JadeCreature();
			crea.creatureModelID = message.modelid;
			var creatures = lobby.jadeteams.get(user._id); // = new JadeCreature[1];
			creatures.add(crea);
//			if(creatures == null || creatures.length == 0) {
//				creatures = new JadeCreature[] { crea };
//			} else {
//				var list = Arrays.asList(creatures);
//				list.add(crea);
//				creatures = (JadeCreature[]) list.toArray();
//			}
			lobby.jadeteams.put(user._id, creatures);
			
			// broadcast select to all lobby participants
			for (var id : lobby.teams.keySet()) {
				Coral.coral.server.users.get(id).writeAndFlush(message);
			}
		} catch (Exception e) {
			Log.info("", e);
			client.channel().close();
		}
	}
	
}
