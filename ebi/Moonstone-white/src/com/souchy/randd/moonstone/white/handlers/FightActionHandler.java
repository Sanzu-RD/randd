package com.souchy.randd.moonstone.white.handlers;

import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.jade.meta.User;
import com.souchy.randd.moonstone.commons.packets.c2s.FightAction;
import com.souchy.randd.moonstone.white.Moonstone;

import io.netty.channel.ChannelHandlerContext;

import org.bson.types.ObjectId;

import com.souchy.randd.commons.diamond.common.Action.*;

public class FightActionHandler implements BBMessageHandler<FightAction> {

	@Override
	public Class<FightAction> getMessageClass() {
		return FightAction.class;
	}

	@Override
	public void handle(ChannelHandlerContext client, FightAction message) {
		Log.info("FightActionHandler handling message [" + message + "]");
//		Fight fight = client.channel().attr(Fight.attrkey).get();
//		var user = client.channel().attr(User.attrkey).get();
		
		
		var action = new SpellAction(Moonstone.fight, message.actionID, message.cellX, message.cellY);
//		var creatureSource = fight.creatures.get(action.caster);
		Log.info("FightActionHandler push " + action);
		
		
		Moonstone.fight.pipe.push(action);
//		action.apply();
		
		// check que le client soit bien le owner de la creature caster 
//		if(creatureSource.get(ObjectId.class).equals(user._id)) {
//			
//			//fight.pipe.push(action);
//		} else {
//			
//		}
		
	}
}
