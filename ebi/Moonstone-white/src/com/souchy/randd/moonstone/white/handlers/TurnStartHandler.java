package com.souchy.randd.moonstone.white.handlers;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessageHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.moonstone.commons.packets.s2c.TurnStart;
import com.souchy.randd.moonstone.white.Moonstone;

import io.netty.channel.ChannelHandlerContext;

public class TurnStartHandler implements BBMessageHandler<TurnStart> {

	/**
	 * dont do endturn when the first turn hasnt even started yet
	 */
	private boolean notfirst = false;
	
	@Override
	public Class<TurnStart> getMessageClass() {
		return TurnStart.class;
	}

	@Override
	public void handle(ChannelHandlerContext client, TurnStart message) {
		if(notfirst) { // || Moonstone.fight.timeline.turn() != 0 || Moonstone.fight.timeline.index() != 0) { // Moonstone.fight.future != null
//			Log.info("TurnStartHandler endturntimer");
			Moonstone.fight.endTurnTimer(); 
		} else {
			notfirst = true;
		}
		
		Moonstone.fight.timeline.setTurn(message.turn);
		Moonstone.fight.timeline.setIndex(message.index);
		Moonstone.fight.time = message.time;

		Moonstone.fight.startTurnTimer();
	}
	
}
