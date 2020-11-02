package com.souchy.randd.moonstone.white;


import java.util.concurrent.TimeUnit;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.common.Action.EndTurnAction;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.statusevents.other.TurnEndEvent;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.EbiShoalCore;
import com.souchy.randd.ebishoal.commons.EbiShoalTCP;
import com.souchy.randd.jade.meta.User;

import io.netty.util.AttributeKey;

public class Moonstone extends EbiShoalTCP { //implements OnTurnStartHandler, OnTurnEndHandler { 
	
	public static final AttributeKey<String[]> authKey = AttributeKey.newInstance("moonstone.white.auth");
	
	public static Moonstone moon;
	public static User user;
	public static Fight fight = new Fight();
	public static EventBus bus = new EventBus();
	
	public Moonstone(String ip, int port, EbiShoalCore core) throws Exception {
		super(ip, port, core);
		moon = this;
//		Moonstone.bus = moon.bus;
		fight.bus.register(this); //.statusbus.register(this);
	}
	
	public static void writes(BBMessage msg) {
		moon.write(msg);
	}
	

//	@Override
//	public HandlerType type() {
//		return HandlerType.Reactor;
//	}

	@Subscribe
	public void onTurnStart(TurnStartEvent e) {
//		Log.format("event fight %s turn %s sta %s", e.fight.id, e.turn, e.index);
//		if(e.fight.future != null) e.fight.future.cancel(true);
//		e.fight.future = e.fight.timer.schedule(() -> {
////			fight.endTurnTimer();
////			moon.write(new PassTurn());
//			fight.bus.post(new TimerTick());
//		}, 1, TimeUnit.SECONDS);
	}

	@Subscribe
	public void onTurnEnd(TurnEndEvent e) {
//		Log.format("event fight %s turn %s end %s", e.fight.id, e.turn, e.index);
	}

	
	// add fight object to here as fieldmember or ecs component ? 
	// but the fight object is already an engine
	// maybe just put the fight here and sapphire will use it instead of putting it in sapphire and having white using it
	// makes more sense
	// why not make it static ?
	
}
