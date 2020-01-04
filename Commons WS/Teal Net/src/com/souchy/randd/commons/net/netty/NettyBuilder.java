package com.souchy.randd.commons.net.netty;

import java.net.URI;

import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider;
import org.glassfish.jersey.server.ResourceConfig;

import com.souchy.randd.commons.net.netty.bytebuf.multihandlers.BBMessageFactories;
import com.souchy.randd.commons.net.netty.bytebuf.multihandlers.BBMessageHandlers;
import com.souchy.randd.commons.net.netty.bytebuf.pipehandlers.BBMessageDecoder;
import com.souchy.randd.commons.net.netty.bytebuf.pipehandlers.BBMessageEncoder;
import com.souchy.randd.commons.net.netty.client.NettyClient;
import com.souchy.randd.commons.net.netty.server.NettyHandler;
import com.souchy.randd.commons.net.netty.server.NettyServer;

import io.netty.channel.Channel;

public class NettyBuilder {
	
	public Channel http(String ip, int port, ResourceConfig rc) throws Exception {
//		String ip = "localhost";
//		int port = 8080;
		
		Channel server = NettyHttpContainerProvider.createHttp2Server(URI.create("http://" + ip + ":" + port + "/"), rc, null);
		// server.pipeline().
		return server;
		// Wait until the server socket is closed.
		//server.closeFuture().sync();
	}
	
	public Channel tcp(int port, BBMessageFactories msgFactories, BBMessageHandlers msgHandlers) throws Exception {
		var serv = NettyServer.create().port(port).SSL(false)
		.encoder(new BBMessageEncoder())
		.decoder(() -> new BBMessageDecoder(msgFactories))
		.handler(new NettyHandler(msgHandlers)).wrap();
		
		return serv.serverChannel;
	}
	
	// Http Client doesnt have a Channel so this cant be a microservice, a bit scuffed
//	public Channel httpClient() {
//		
//	}
	
	public Channel tcpClient(String ip, int port, BBMessageFactories msgFactories, BBMessageHandlers msgHandlers) throws Exception {
		NettyClient client = NettyClient.create(ip, port, false, true, new BBMessageEncoder(), new BBMessageDecoder(msgFactories), new NettyHandler(msgHandlers));
		return client.channel;
	}
	
}
