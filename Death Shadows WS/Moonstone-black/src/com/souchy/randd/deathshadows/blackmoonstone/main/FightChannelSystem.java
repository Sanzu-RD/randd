package com.souchy.randd.deathshadows.blackmoonstone.main;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.ecs.Family;

import io.netty.channel.Channel;

public class FightChannelSystem extends Family<Channel> {

	public FightChannelSystem(Engine engine) {
		super(engine, Channel.class);
	}

	@Override
	public void update(float delta) {
		
	}

	public void broadcast(BBMessage msg) {
		this.foreach(c -> c.writeAndFlush(msg));
	}
	
}
