package com.souchy.randd.ebishoal.commons;

import com.souchy.randd.commons.net.netty.bytebuf.pipehandlers.BBMessageDecoder;
import com.souchy.randd.commons.net.netty.bytebuf.pipehandlers.BBMessageEncoder;
import com.souchy.randd.commons.net.netty.client.NettyClient;
import com.souchy.randd.commons.net.netty.server.NettyHandler;

public abstract class EbiCoreClient extends EbiCore {
	
	/**
	 * Netty client
	 */
	public NettyClient client;

	/**
	 * Initialize the client core
	 * @throws Exception
	 */
	@Override
	public void init() throws Exception {
		// ebishoal.config
		client = NettyClient.create(
				getIp(), getPort(), getSsl(), false,
				new BBMessageEncoder(), 
				new BBMessageDecoder(msgFactories), 
				new NettyHandler(msgHandlers)
		);
	}

	@Override
	public void start() {
		client.start();
	}

	protected abstract String getIp();
	protected abstract int getPort();
	protected abstract boolean getSsl();

}
