package com.souchy.randd.deathshadows.commons.core;

import com.souchy.randd.commons.net.netty.bytebuf.pipehandlers.BBMessageDecoder;
import com.souchy.randd.commons.net.netty.bytebuf.pipehandlers.BBMessageEncoder;
import com.souchy.randd.commons.net.netty.server.NettyHandler;
import com.souchy.randd.commons.net.netty.server.NettyServer;
import com.souchy.randd.commons.net.netty.server.NettyServerBuilderHolder.NettyServerBuilderEncoder;
import com.souchy.randd.commons.net.netty.server.NettyServerBuilderHolder.NettyServerBuilderPort;
import com.souchy.randd.commons.net.netty.server.NettyServerBuilderHolder.NettyServerBuilderWrapper;

public abstract class CoreServer extends DeathShadowCore {

	/**
	 * Netty server
	 */
	public NettyServer serv;

	/**
	 * Initialize the server core
	 * @throws Exception
	 */
	@Override
	public void init() throws Exception {
		// Init a NettyServer Builder 
		NettyServerBuilderEncoder pipeBuilder = initServer(NettyServer.create());
		// Init the server pipeline 
		NettyServerBuilderWrapper conf = initPipeline(pipeBuilder);
		// Build the server
		serv = conf/* .adapt() */.wrap();
	}

	@Override
	public void start() throws Exception {
		serv.start();
	}
	
	/**
	 * Must be implemented by the Core instance to configure its NettyServer via the NettyServerBuilder
	 * <br>
	 * Serves to set the IP, port and SSL settings
	 * 
	 * @param builder - A NettyServerBuilder at the port step
	 * 
	 * @return The same builder a couple steps forward
	 */
	protected abstract NettyServerBuilderEncoder initServer(NettyServerBuilderPort builder);
	
	/**
	 * Self-explanatory
	 * @param pipeBuilder - NettyServerBuilder at the pipeline encoder step
	 * @return A completed NettyServerBuilder configuration
	 */
	protected NettyServerBuilderWrapper initPipeline(NettyServerBuilderEncoder pipeBuilder) { //ChannelPipeline pipe);
		return pipeBuilder
				.encoder(new BBMessageEncoder())
				.decoder(() -> new BBMessageDecoder(msgFactories))
				.handler(new NettyHandler(msgHandlers));
	}

	
}
