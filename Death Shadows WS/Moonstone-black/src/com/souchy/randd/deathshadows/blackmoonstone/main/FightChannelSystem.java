package com.souchy.randd.deathshadows.blackmoonstone.main;

import com.souchy.randd.commons.diamond.common.ecs.Engine;
import com.souchy.randd.commons.diamond.common.ecs.Family;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;

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
