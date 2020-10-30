package com.souchy.randd.deathshadows.blackmoonstone.main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.common.Action.EndTurnAction;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.statics.Constants;
import com.souchy.randd.commons.diamond.statusevents.Handler;
import com.souchy.randd.commons.diamond.statusevents.other.TurnEndEvent;
import com.souchy.randd.commons.diamond.statusevents.other.TurnEndEvent.OnTurnEndHandler;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent.OnTurnStartHandler;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadow.core.DeathShadowCore;
import com.souchy.randd.deathshadow.core.DeathShadowTCP;
import com.souchy.randd.deathshadow.core.handlers.AuthenticationFilter.UserActiveEvent;
import com.souchy.randd.deathshadow.core.handlers.AuthenticationFilter.UserInactiveEvent;
import com.souchy.randd.deathshadow.core.smoothrivers.SelfIdentify;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.jade.meta.User;
import com.souchy.randd.jade.meta.UserLevel;
import com.souchy.randd.moonstone.commons.packets.s2c.TurnStart;

import io.netty.channel.Channel;

public class BlackMoonstone extends DeathShadowCore { // implements OnTurnEndHandler, OnTurnStartHandler {
	
	/**
	 * Static ref
	 */
	public static BlackMoonstone moon;
	
	/**
	 * TCP Server
	 */
	public final DeathShadowTCP server;
	
	/**
	 * One server can handle multiple fights
	 */
	public Map<Integer, Fight> fights;
	
	
	public static void main(String[] args) throws Exception {
		new BlackMoonstone(args);
	}
	
	public BlackMoonstone(String[] args) throws Exception {
		super(args);
		moon = this;
		port = 443; // port changeant pour chaque node instance
		if(args.length > 0) port = Integer.parseInt(args[0]);
		
		server = new DeathShadowTCP(port, this);
		fights = new HashMap<>();
		server.auth.bus.register(this);
		
		// create a mock fight for tests
		if(Arrays.asList(args).contains("mock")) {
			var fight = MockFight.createFight();
			new FightChannelSystem(fight);
			fight.bus.register(this); //.statusbus.register(this);
			fight.startTurnTimer();
			fights.put(fight.id, fight);
		}
		if(Arrays.asList(args).contains("mock")) {
			var fight = MockFight.createFight();
			new FightChannelSystem(fight);
			fight.bus.register(this); //.statusbus.register(this);
			fight.startTurnTimer();
			fights.put(fight.id, fight);
		}

		// register node on pearl
//		 rivers.consume("idmaker" () -> {
//			int nodeid = 0; // get nodeid from idmaker queue
//		 rivers.send("pearl", new SelfIdentify(nodeid));
//		 });

		this.rivers.connect(port);
		this.rivers.sendPearl(new SelfIdentify(this));
		
		
		// block here to not just exit the program
		if(!Arrays.asList(args).contains("async"))
			server.block();

	}
	
	public static void broadcast(Fight f, BBMessage m) {
		var syst = f.get(FightChannelSystem.class);
//		Log.format("broadcast to system size %s", syst.size());
		syst.broadcast(m);
	}

	@Subscribe
	public void onConnect(UserActiveEvent e) {
		Log.info("black on connect : " + e.user.username);
//		Fight fight = e.ctx.channel().attr(Fight.attrkey).get();
//		var user = e.ctx.channel().attr(User.attrkey).get();
//		fight.creatures.foreach(c -> c.add(user._id));
	}
	
	@Subscribe
	public void onDisconnect(UserInactiveEvent e) {
		Log.info("black on disconnect : " + e.user.username);
		Fight fight = e.ctx.channel().attr(Fight.attrkey).get();
		fight.get(FightChannelSystem.class).remove(e.ctx.channel());
	}


	@Subscribe
	public void onTurnStart(TurnStartEvent e) {
//		Log.format("event fight %s turn %s sta %s", e.fight.id, e.turn, e.index);
		
		if(e.fight.future != null) e.fight.future.cancel(true);
		
		e.fight.time = Constants.baseTimePerTurn;
		
		var action = new EndTurnAction(e.fight);
		e.fight.future = e.fight.timer.scheduleAtFixedRate(() -> {
			if(e.fight.time > 0) e.fight.time--;
			else e.fight.pipe.push(action);
		}, 1, 1, TimeUnit.SECONDS);

		broadcast(e.fight, new TurnStart(e.turn, e.index, e.fight.time));
	}
	
	@Subscribe
	public void onTurnEnd(TurnEndEvent e) {
//		Log.format("event fight %s turn %s end %s", e.fight.id, e.turn, e.index);
	}
	
	
	@Override
	protected String[] getRootPackages() {
		return new String[]{ 
				"com.souchy.randd.commons.deathebi.msg", 
				"com.souchy.randd.deathshadow.core.handlers", 
				"com.souchy.randd.deathshadow.core.smoothrivers", 
				"com.souchy.randd.deathshadows.nodes.pearl.messaging",
				"com.souchy.randd.deathshadows.nodes.pearl.messaging.moonstone",
				"com.souchy.randd.moonstone", 
				"com.souchy.randd.deathshadows.nodes.moonstone.black",
				"com.souchy.randd.deathshadows.blackmoonstone.handlers",
				"com.souchy.randd.deathshadows.blackmoonstone.riverhandlers"
				};
	}

//	@Override
//	public HandlerType type() {
//		return HandlerType.Reactor;
//	}

}
