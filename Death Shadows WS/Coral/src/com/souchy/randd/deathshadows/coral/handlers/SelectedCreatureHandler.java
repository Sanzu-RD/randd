package com.souchy.randd.deathshadows.coral.handlers;

import com.souchy.randd.commons.coral.draft.SelectCreature;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.coral.main.Coral;
import com.souchy.randd.deathshadows.coral.main.OnTurnEndAction;
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
	public void handle(ChannelHandlerContext client, SelectCreature msg) {
		try {

//			Log.format("Coral on select creature (%s)", msg.modelid);
			
			// if the creature model doesnt exist, cancel
			if(!DiamondModels.creatures.containsKey(msg.modelid)) return;
			
			User user = client.channel().attr(User.attrkey).get();
			Lobby lobby = client.channel().attr(Lobby.attrkey).get();

			// ignore if the user is not the current player
			if(lobby.getCurrentPlayer() != user._id) return;

			// ignore if pick/ban phase is over
			if(lobby.isPickbanOver()) return;
			
//			Log.format("Coral on select creature (%s), user %s, lobby %s", msg.modelid, user, lobby);
			
			// fix team unless it's a mocking lobby
			if(lobby.type != GameQueue.mock) msg.team = lobby.team(user._id);
			msg.turn = lobby.turn();
			
			// set turn on action
			var action = new OnTurnEndAction(lobby);
			
			// end turn
			lobby.pipe().push(action);
			
			// add new jade creature
			var crea = new JadeCreature();
			crea.creatureModelID = msg.modelid;
			var creatures = lobby.creatures(user._id); 
			creatures.add(crea);
			
			// broadcast select to all lobby participants
			Coral.broadcast(lobby, msg);
			

			// if pick/ban phase is over
			if(lobby.isPickbanOver()) {
//				asdf
				// nothing to do actually ?
				// client already knows what phase it is from the turn value
			}
			
			
		} catch (Exception e) {
			Log.info("", e);
			client.channel().close();
		}
	}
	
}
