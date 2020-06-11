package com.souchy.randd.deathshadow.core;

import com.souchy.randd.commons.deathebi.Microservice;
import com.souchy.randd.commons.net.netty.NettyBuilder;
import com.souchy.randd.commons.net.netty.server.NettyHandler;
import com.souchy.randd.commons.net.netty.server.NettyServer;
import com.souchy.randd.commons.tealwaters.commons.Factory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

public abstract class DeathShadowService extends Microservice {

	@Override
	protected void preinit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Channel initNetty(NettyBuilder builder) throws Exception {
		//new NettyServer();
		return null;
	}
	
	
	public static class DSS extends NettyServer {

		DSS(int port, boolean ssl, MessageToByteEncoder<?> encoder, Factory<ByteToMessageDecoder> decoder, NettyHandler handler) throws Exception {
			super(port, ssl, encoder, decoder, handler);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void initPipeline(ChannelPipeline pipe) {
			//pipe.addLast(connectionHandler);
			pipe.addLast(decoder.create()); 
			pipe.addLast(encoder); 
			pipe.addLast(handler); 
		}
		
	}
	
}
