package com.souchy.randd.deathshadow.core;

import org.bson.types.ObjectId;
import java.util.Map;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.commons.net.netty.bytebuf.pipehandlers.BBMessageDecoder;
import com.souchy.randd.commons.net.netty.bytebuf.pipehandlers.BBMessageEncoder;
import com.souchy.randd.commons.net.netty.server.NettyHandler;
import com.souchy.randd.commons.net.netty.server.NettyServer;
import com.souchy.randd.deathshadow.core.handlers.AuthenticationFilter;
import com.souchy.randd.jade.meta.User;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;

/**
 * Netty server with an AuthenticationFilter 
 * 
 * @author Blank
 * @date 31 déc. 2019
 */
public class DeathShadowTCP extends NettyServer {


	public final AuthenticationFilter auth = new AuthenticationFilter();
	public final Map<ObjectId, Channel> users = auth.userChannels;
	
	/**
	 * Create & Start the server. Should call .block() after to wait for server closure before exiting the application <br>
	 * Example : <br>
	 * <code>
	 * var server = new DeathShadowTCP(port, this); <br>
	 * server.block();
	 * </code>
	 *
	 */
	public DeathShadowTCP(int port, DeathShadowCore core) throws Exception {
		super(port, false, new BBMessageEncoder(), () -> new BBMessageDecoder(core.getMessages()), new NettyHandler(core.getHandlers()));
	}
	
	@Override
	public void initPipeline(ChannelPipeline pipe) {
		pipe.addLast(connectionHandler);
		
		pipe.addLast(lengthEncoder); 
		pipe.addLast(encoder); 

		pipe.addLast(lengthDecoder.create()); 
		pipe.addLast(decoder.create()); 
		pipe.addLast(auth); // auth avant le packet handler
		pipe.addLast(handler); 
	}

//	public Map<ObjectId, Channel> users(){
//		return users;
//	}
	
}
