package com.souchy.randd.deathshadows.blackmoonstone.handlers;

import org.bson.types.ObjectId;

import com.souchy.randd.commons.diamond.common.Action.EndTurnAction;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.jade.meta.User;
import com.souchy.randd.moonstone.commons.packets.c2s.PassTurn;

import io.netty.channel.ChannelHandlerContext;

public class PassTurnHandler implements BBMessageHandler<PassTurn> {

	@Override
	public void handle(ChannelHandlerContext client, PassTurn message) {
		// 
		Fight fight = client.channel().attr(Fight.attrkey).get();
		
		var action = new EndTurnAction(fight);
		var creature = fight.creatures.get(action.caster);
		var user = client.channel().attr(User.attrkey).get();
		
//		Log.format("passturn   user %s creature %s owner %s %s turn %s i %s", user._id, creature.id, creature.get(ObjectId.class), creature.get(ObjectId.class).equals(user._id), action.turn, fight.timeline.index());
		
		if(creature.get(ObjectId.class).equals(user._id)) {
			fight.pipe.push(action);
		}
		
//		var caster = fight.timeline.get(message.index);
//		action.caster = caster == null ? -1 : caster;
//		action.turn = message.turn;
//		fight.get(FightChannelSystem.class).broadcast(message);
	}

	@Override
	public Class<PassTurn> getMessageClass() {
		return PassTurn.class;
	}
	
}
