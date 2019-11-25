package com.souchy.randd.deathshadows.opal;

import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider;
import org.glassfish.jersey.server.ResourceConfig;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadows.commons.core.DeathShadowCore;

import io.netty.channel.Channel;

/**
 * 
 * HTTP API Server, Used to authenticate users and access db data
 * 
 * @author Blank
 *
 */
public class Opal extends DeathShadowCore {
	
	private static Channel server;
	private static ResourceConfig rc;
	private static EventBus bus = new EventBus();
	
	public static void main(String args[]) throws Exception {
		launch(new Opal());
	}

	@Override
	public void init() throws Exception {
		// Config
		rc = new ResourceConfig()
			.packages(getRootPackages())
			//.register(Test.class)
//			.register(FTLViewProcessor.class)
//			.property(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, "")
//			.register(FreemarkerMvcFeature.class) 
	   //     .register(RolesAllowedDynamicFeature.class)
			;
		// Log toutes les classes registered
		Log.info("Opal classes :");
		rc.getClasses().forEach(System.out::println);
	}
	
	@Override
	public void start() {
		String ip = "localhost";
		int port = 8080;
		boolean grizzly = false;
		
		try {
			// Server
			if(grizzly) {
				@SuppressWarnings("unused")
				HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://" + ip + ":" + port + "/"), rc);
			} else {
				server = NettyHttpContainerProvider.createHttp2Server(URI.create("http://" + ip + ":" + port + "/"), rc, null);
				// server.pipeline().
				Runtime.getRuntime().addShutdownHook(new Thread(() -> server.close()));
			}
			Thread.currentThread().join();
			throw new InterruptedException("asdf");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public EventBus getBus() {
		return bus;
	}
	
	@Override
	protected String[] getRootPackages() {
		return new String[] { "com.souchy.randd.deathshadows.opal" };
	}

	
}
