package com.souchy.randd.deathshadows.blackmoonstone.handlers;

import org.bson.types.ObjectId;

import com.souchy.randd.commons.diamond.common.Action.*;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.blackmoonstone.main.BlackMoonstone;
import com.souchy.randd.deathshadows.blackmoonstone.main.FightChannelSystem;
import com.souchy.randd.jade.meta.User;
import com.souchy.randd.moonstone.commons.packets.c2s.FightAction;

import io.netty.channel.ChannelHandlerContext;

public class FightActionHandler implements BBMessageHandler<FightAction> {

	@Override
	public Class<FightAction> getMessageClass() {
		return FightAction.class;
	}

	@Override
	public void handle(ChannelHandlerContext client, FightAction message) {
		Log.info("FightActionHandler handling message [" + message + "]");
		Fight fight = client.channel().attr(Fight.attrkey).get();
		var user = client.channel().attr(User.attrkey).get();
		if(fight == null) return;
		if(user == null) return;

		var action = new SpellAction(fight, message.actionID, message.cellX, message.cellY);
		var creatureSource = fight.creatures.get(action.caster);

		// check que le client soit bien le owner de la creature caster
		if (creatureSource != null && creatureSource.get(ObjectId.class).equals(user._id)) {
			Log.info("FightActionHandler push " + action);
			fight.pipe.push(action);
		} else {
			Log.info("FightActionHandler discard " + action);
		}
		
	}

}
