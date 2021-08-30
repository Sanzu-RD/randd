package com.souchy.randd.deathshadows.blackmoonstone.main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.common.Action;
import com.souchy.randd.commons.diamond.common.Action.EndTurnAction;
import com.souchy.randd.commons.diamond.common.Action.SpellAction;
import com.souchy.randd.commons.diamond.main.DiamondModels;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.jade.Constants;
import com.souchy.randd.commons.diamond.statusevents.Handler;
import com.souchy.randd.commons.diamond.statusevents.Handler.Reactor;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent.OnCastSpellHandler;
import com.souchy.randd.commons.diamond.statusevents.other.TurnEndEvent;
import com.souchy.randd.commons.diamond.statusevents.other.TurnEndEvent.OnTurnEndHandler;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent.OnTurnStartHandler;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.deathshadow.core.DeathShadowCore;
import com.souchy.randd.deathshadow.core.DeathShadowTCP;
import com.souchy.randd.deathshadow.core.handlers.AuthenticationFilter.UserActiveEvent;
import com.souchy.randd.deathshadow.core.handlers.AuthenticationFilter.UserInactiveEvent;
import com.souchy.randd.deathshadow.core.smoothrivers.SelfIdentify;
import com.souchy.randd.deathshadows.iolite.emerald.Emerald;
import com.souchy.randd.jade.meta.User;
import com.souchy.randd.jade.meta.UserLevel;
import com.souchy.randd.moonstone.commons.packets.c2s.FightAction;
import com.souchy.randd.moonstone.commons.packets.s2c.TurnStart;

import io.netty.channel.Channel;

public class BlackMoonstone extends DeathShadowCore implements Reactor, OnTurnStartHandler, OnTurnEndHandler, OnCastSpellHandler {
	
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
		
		this.bus.register(this);
		
		server = new DeathShadowTCP(port, this);
		fights = new HashMap<>();
		server.auth.bus.register(this);
		
		// init elements and models
		Elements.values(); 
		DiamondModels.instantiate("com.souchy.randd.data.s1");
		
		// create a mock fight for tests
		if (Arrays.asList(args).contains("mock")) {
			for (int count = 0; count < 2; count++) {
				var fight = MockFight.createFight();
				new FightChannelSystem(fight);
				fight.pipe.setBus(this.bus); // new EventBus();
				// fight.pipe.bus.register(this); // register to pipeline actions
				fight.statusbus.register(this); // register to fight events
				fight.startTurnTimer();
				fights.put(fight.id, fight);
			}
		}

		// register node on pearl
//		 rivers.consume("idmaker" () -> {
//			int nodeid = 0; // get nodeid from idmaker queue
//		 rivers.send("pearl", new SelfIdentify(nodeid));
//		 });
		
		this.rivers.connect(port);
		this.rivers.sendPearl(new SelfIdentify(this));
		
		Log.info("-------------------------------------------------------------------");
		Log.info("---------------------- Blackmoonstone online ----------------------");
		Log.info("-------------------------------------------------------------------");
		
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
		Log.info("BlackMoonstone on connect : " + e.user.username);
//		Fight fight = e.ctx.channel().attr(Fight.attrkey).get();
//		var user = e.ctx.channel().attr(User.attrkey).get();
//		fight.creatures.foreach(c -> c.add(user._id));
	}
	
	@Subscribe
	public void onDisconnect(UserInactiveEvent e) {
		Log.info("BlackMoonstone on disconnect : " + e.user);
		Fight fight = e.ctx.channel().attr(Fight.attrkey).get();
		fight.get(FightChannelSystem.class).remove(e.ctx.channel());
	}

	@Override
	public void onTurnStart(TurnStartEvent e) {
		//Log.format("BlackMoonstone event fight %s turn %s sta %s " + e, e.fight.id, e.turn, e.index);
		
		if(e.fight.future != null) e.fight.future.cancel(true);
		
		e.fight.time = Constants.baseTimePerTurn;
		
		var action = new EndTurnAction(e.fight);
		e.fight.future = e.fight.timer.scheduleAtFixedRate(() -> {
			if(e.fight.time > 0) e.fight.time--;
			else e.fight.pipe.push(action);
		}, 1, 1, TimeUnit.SECONDS);

		broadcast(e.fight, new TurnStart(e.turn, e.index, e.fight.time));
	}

	@Override
	public void onTurnEnd(TurnEndEvent e) {
		//Log.format("BlackMoonstone event fight %s turn %s end %s", e.fight.id, e.turn, e.index);
	}
	
	
	@Override
	public void onCastSpell(CastSpellEvent event) {
		//event.source.get(Fight.class).get(FightChannelSystem.class).broadcast(message);
	}
	
	@Subscribe
	public void onPipelineFightAction(SpellAction action) {
		if(action != null) {
			Log.info("BlackMoonstone on SpellAction");
			if(action instanceof SpellAction)
				action.fight.get(FightChannelSystem.class)
					.broadcast(new FightAction(action.caster, action.turn, action.id, action.cellX, action.cellY));
		}
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


}
