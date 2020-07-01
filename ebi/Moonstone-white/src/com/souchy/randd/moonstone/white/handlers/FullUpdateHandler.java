package com.souchy.randd.moonstone.white.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.moonstone.commons.packets.s2c.FullUpdate;
import com.souchy.randd.moonstone.white.Moonstone;

import data.new1.timed.Status;
import gamemechanics.models.Cell;
import gamemechanics.models.Creature;
import gamemechanics.models.Spell;
import io.netty.channel.ChannelHandlerContext;

public class FullUpdateHandler implements BBMessageHandler<FullUpdate> {

	@Override
	public void handle(ChannelHandlerContext ctx, FullUpdate message) {
		
		// clear families before adding the updated ones
		synchronized (Moonstone.fight.cells.family) {
//			Moonstone.fight.entities.removeIf(e -> e instanceof Cell);
			Moonstone.fight.cells.family.clear();
			message.cells.forEach(c -> c.register(Moonstone.fight));
		}
		synchronized (Moonstone.fight.creatures.family) {
//			Moonstone.fight.entities.removeIf(e -> e instanceof Creature);
			Moonstone.fight.creatures.family.clear();
			message.creatures.forEach(c -> c.register(Moonstone.fight));
		}
		synchronized (Moonstone.fight.status.family) {
//			Moonstone.fight.entities.removeIf(e -> e instanceof Status);
			Moonstone.fight.status.family.clear();
			message.status.forEach(c -> c.register(Moonstone.fight));
		}
		synchronized (Moonstone.fight.spells.family) {
//			Moonstone.fight.entities.removeIf(e -> e instanceof Spell);
			Moonstone.fight.spells.family.clear();
			message.spells.forEach(c -> c.register(Moonstone.fight));
		}
		
//		Log.info("FullUpdate cells previous intances " + Moonstone.fight.cells.family.size());
//		Log.info("FullUpdate creatures previous intances " + Moonstone.fight.creatures.family.size());
		

		Log.info("FullUpdate cells intances " + Moonstone.fight.cells.family.size());
		Log.info("FullUpdate creatures intances " + Moonstone.fight.creatures.family.size());
		Log.info("FullUpdate spells intances " + Moonstone.fight.spells.family.size());
		Log.info("FullUpdate status intances " + Moonstone.fight.status.family.size());

		// propagate event
		Moonstone.bus.post(message);
	}

	@Override
	public Class<FullUpdate> getMessageClass() {
		return FullUpdate.class;
	}

}
