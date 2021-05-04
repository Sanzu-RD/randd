package com.souchy.randd.deathshadow.core;

import java.net.URI;

import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider;
import org.glassfish.jersey.server.ResourceConfig;

import com.souchy.randd.commons.tealwaters.logging.Log;

import io.netty.channel.Channel;

public class DeathShadowHTTP {

	public Channel channel;
	public ResourceConfig rc;
	private String ip = "localhost";
	private int port = 8080;
	
	public DeathShadowHTTP(String ip, int port, String[] rootPackages) {
		this(ip, port, rootPackages, new Class<?>[0]);
	}

	public DeathShadowHTTP(String ip, int port, String[] rootPackages, Class<?>... registerClasses) {
		this.ip = ip;
		this.port = port;
		rc = new ResourceConfig();
		rc.packages(rootPackages); 
		rc.registerClasses(registerClasses);
		for(var c : rc.getClasses()) Log.info("Opal registered " + c);
		start();
	}
	
	private void start() {
		channel = NettyHttpContainerProvider.createHttp2Server(URI.create("http://" + ip + ":" + port + "/"), rc, null);
	}

	public void block() {
		// Wait until the server socket is closed.
		try {
			channel.closeFuture().sync();
		} catch (InterruptedException e) {
			Log.error("", e);
		}
	}
	
	/*
	public void asdf() {
		// boolean grizzly = false;
		try {
			// Server
			// if(grizzly) {
			// @SuppressWarnings("unused")
			// HttpServer server =
			// GrizzlyHttpServerFactory.createHttpServer(URI.create("http://" + ip + ":" +
			// port + "/"), rc);
			// } else {
			channel = NettyHttpContainerProvider.createHttp2Server(URI.create("http://" + ip + ":" + port + "/"), rc, null);
			// server.pipeline().
			// Runtime.getRuntime().addShutdownHook(new Thread(() -> server.close()));
			// }
			// Thread.currentThread().join();
			throw new InterruptedException("asdf");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	*/
	
}
