package com.souchy.randd.moonstone.white.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.moonstone.commons.packets.s2c.FullUpdate;
import com.souchy.randd.moonstone.white.Moonstone;

import io.netty.channel.ChannelHandlerContext;

public class FullUpdateHandler implements BBMessageHandler<FullUpdate> {

	@Override
	public void handle(ChannelHandlerContext ctx, FullUpdate message) {
		
		// clear families before adding the updated ones
		Moonstone.fight.cells.family.clear();
		Moonstone.fight.creatures.family.clear();
		Moonstone.fight.status.family.clear();
		Moonstone.fight.spells.family.clear();
		
//		Log.info("FullUpdate cells previous intances " + Moonstone.fight.cells.family.size());
//		Log.info("FullUpdate creatures previous intances " + Moonstone.fight.creatures.family.size());
		
		message.cells.forEach(c -> c.register(Moonstone.fight));
		message.creatures.forEach(c -> c.register(Moonstone.fight));
		message.status.forEach(c -> c.register(Moonstone.fight));
		message.spells.forEach(c -> c.register(Moonstone.fight));

		Log.info("FullUpdate cells intances " + Moonstone.fight.cells.family.size());
		Log.info("FullUpdate creatures intances " + Moonstone.fight.creatures.family.size());
		Log.info("FullUpdate spells intances " + Moonstone.fight.spells.family.size());
		Log.info("FullUpdate status intances " + Moonstone.fight.status.family.size());

		// player hud
//		SapphireHudSkin.play(fight.teamA.get(0));
	}

	@Override
	public Class<FullUpdate> getMessageClass() {
		return FullUpdate.class;
	}

}
