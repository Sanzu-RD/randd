package com.souchy.randd.deathshadows.pearl.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;
import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer.DefaultClassDiscoverer;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.commons.tealwaters.logging.Logging;
import com.souchy.randd.deathshadow.core.DeathShadowCore;
//import com.souchy.randd.deathshadows.commons.core.CoreServer;
import com.souchy.randd.deathshadow.core.DeathShadowTCP;
import com.souchy.randd.deathshadow.core.smoothrivers.AskIdentifications;
import com.souchy.randd.deathshadow.core.smoothrivers.SelfIdentify;
import com.souchy.randd.deathshadow.core.smoothrivers.SmoothRivers;
import com.souchy.randd.deathshadows.blackmoonstone.main.BlackMoonstone;
import com.souchy.randd.deathshadows.coral.main.Coral;
import com.souchy.randd.deathshadows.opal.Opal;
import com.souchy.randd.deathshadows.pearl.NodeInfo;
import com.souchy.randd.jade.meta.UserLevel;

/**
 * 
 * Server for managin/monitoring and messaging between server nodes
 * 
 * Not date of creation, but modernisation.
 * 
 * Will need to run it by command from a batch file that loops so it reboots & uses the latest jar automatically everytime it exits
 * 
 * @author Blank
 * @date 27 mai 2020
 */
public final class Pearl extends DeathShadowCore {
	
	public static Pearl core;
	
	public final DeathShadowTCP server;
	
	/**
	 * Server Core class types
	 */
//	private final List<Class<DeathShadowCore>> deathShadowCoreTypes = new ArrayList<>();
	
	/**
	 * Physical node servers currently running
	 */
//	public final List<NodeInfo> nodes = new ArrayList<>();
	
	// <Type, Port> ports
	// <Type, Info> infos
	public final static Map<Class<? extends DeathShadowCore>, Stack<NodeInfo>> nodes = new HashMap<>();
	
	@SuppressWarnings("serial")
	public final static Map<Class<? extends DeathShadowCore>, Integer> basePorts = new HashMap<>() {{
	    put(Opal.class, 8000);
	    put(Coral.class, 7000);
	    put(BlackMoonstone.class, 6000);
	    put(Pearl.class, 1000);
	}};
	
	static {
		var classes = new DefaultClassDiscoverer<DeathShadowCore>(DeathShadowCore.class).explore("com.souchy.randd.deathshadows");
		classes.forEach(c -> nodes.put(c, new Stack<>()));
		Log.info("Pearl DeathShadowCore types : " + String.join(", ", nodes.keySet().stream().map(t -> t.getSimpleName()).collect(Collectors.toList())));
	}
	
	public static void main(String[] args) throws Exception {
		new Pearl(args);
	}
	
	public Pearl(String[] args) throws Exception {
		super(args);
		core = this;
		
		this.port = 1000;
		var root = "../../../"; // GitPiranha root path which contains 'r and d' and 'release' repositories
		if(args.length > 0) port = Integer.parseInt(args[0]);
		if(args.length > 1) root = args[1];

		Environment.root = Paths.get(root);
		
		// start server
		server = new DeathShadowTCP(port, this); 
		server.auth.minLevel = UserLevel.Creator;
		
		// start rivers
		this.rivers.connect(port);
//		SmoothRivers.sendPearl(new SelfIdentify(this));
		SmoothRivers.send("nodes", new AskIdentifications());
		
		server.block(); 
	}


	@Override
	protected String[] getRootPackages() {
		return new String[]{
				"com.souchy.randd.commons.deathebi", 
				"com.souchy.randd.deathshadow.core.handlers", 
				"com.souchy.randd.deathshadow.core.smoothrivers", 
				"com.souchy.randd.deathshadows.pearl", "com.souchy.randd.deathshadows.pearl.handlers", "com.souchy.randd.deathshadows.pearl.rabbithandlers", 
				"com.souchy.randd.deathshadows.nodes.pearl.messaging", "com.souchy.randd.deathshadows.nodes.pearl.messaging.moonstone",
				"com.souchy.randd.deathshadows.nodes.pearl.messaging.coral", "com.souchy.randd.deathshadows.nodes.pearl.messaging.opal"  
				};
	}

	
	// node shall get an id themselves from the idqueue and then selfidentify to pearl
	public int tempIdmaker = 1;
	public NodeInfo create(String coreName) {
		try {
//			coreName = coreName.toLowerCase();
			Log.info("Pearl create node 1 : " + coreName);
			for(var type : nodes.keySet()) {
				
				var typename = type.getSimpleName().toLowerCase();
				Log.info("Pearl create node 2 : " + coreName + " ?= " + typename);
				
				if(!coreName.equalsIgnoreCase(typename)) continue;
				
				Log.info("Pearl create node 2 : " + coreName + " == " + type);
				
				createProcess(type);
				
				return null;
			}
		} catch (Exception e) {
			Log.error("", e);
		}
		return null;
	}

	public static void createProcess(Class<? extends DeathShadowCore> type) {
		var typelist = nodes.get(type);
		int port = basePorts.get(type) + typelist.size();
		createProcess(type, port);
	}
	
	public static void createProcess(Class<? extends DeathShadowCore> type, int port) {
		try {
			var typename = type.getSimpleName().toLowerCase();
			var file = Environment.fromRoot("/release/deathshadows/" + typename + ".jar").toFile();
			var path = file.getAbsolutePath();
			var command = "java -jar --enable-preview \"" + path + "\" " + port;
			
			Log.info("Pearl create node 2 file " + file);
			Log.info("Pearl create node 2 command " + command);
			
			Process proc = Runtime.getRuntime().exec(command);
			
			StreamGobbler streamGobblerError = new StreamGobbler(proc.getErrorStream(), (s) -> Log.defferedError(typename + "(" + proc.pid() + ")", s));
			Executors.newSingleThreadExecutor().submit(streamGobblerError);
			
			StreamGobbler streamGobbler = new StreamGobbler(proc.getInputStream(), (s) -> Log.deffered(typename + "(" + proc.pid() + ")", s));
			Executors.newSingleThreadExecutor().submit(streamGobbler);
			
			Log.info("Pearl create process : " + proc);
		} catch (Exception e) {
			Log.error("", e);
		}
	}


	
	private static class StreamGobbler implements Runnable {
		private InputStream inputStream;
		private Consumer<String> consumer;
		public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
			this.inputStream = inputStream;
			this.consumer = consumer;
		}
		@Override
		public void run() {
			new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
		}
	}
	
}
