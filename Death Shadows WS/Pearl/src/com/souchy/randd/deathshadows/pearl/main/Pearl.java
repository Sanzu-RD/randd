package com.souchy.randd.deathshadows.pearl.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;
import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer.DefaultClassDiscoverer;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadow.core.DeathShadowCore;
//import com.souchy.randd.deathshadows.commons.core.CoreServer;
import com.souchy.randd.deathshadow.core.DeathShadowTCP;
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
	private final List<Class<DeathShadowCore>> deathShadowCoreTypes = new ArrayList<>();
	
	/**
	 * Physical node servers currently running
	 */
	public final List<NodeInfo> nodes = new ArrayList<>();
	
	
	
	public static void main(String[] args) throws Exception {
		new Pearl(args);
	}
	
	public Pearl(String[] args) throws Exception {
		super(args);
		core = this;
		int port = 1000;
		// GitPiranha root path which contains 'r and d' and 'release' repositories
		String root = "../../../";
		if(args.length > 0) port = Integer.parseInt(args[0]);
		if(args.length > 1) root = args[1];

		Environment.root = Paths.get(root);
		deathShadowCoreTypes.addAll(new DefaultClassDiscoverer<DeathShadowCore>(DeathShadowCore.class).explore("com.souchy.randd.deathshadows"));
		Log.info("Pearl DeathShadowCore types : " + String.join(", ", deathShadowCoreTypes.stream().map(t -> t.getSimpleName()).collect(Collectors.toList())));
		
//		rivers.connect();
		
		// start server
		server = new DeathShadowTCP(port, this); 
		server.auth.minLevel = UserLevel.Creator;
		server.block(); 
	}


	@Override
	protected String[] getRootPackages() {
		return new String[]{
				"com.souchy.randd.commons.deathebi", 
				"com.souchy.randd.deathshadows.pearl", "com.souchy.randd.deathshadows.pearl.handlers", 
				"com.souchy.randd.deathshadows.nodes.pearl.messaging", "com.souchy.randd.deathshadows.nodes.pearl.messaging.moonstone",
				"com.souchy.randd.deathshadows.nodes.pearl.messaging.coral", "com.souchy.randd.deathshadows.nodes.pearl.messaging.opal"  
				};
	}

	
	// node shall get an id themselves from the idqueue and then selfidentify to pearl
	private int tempIdmaker = 1;
	public NodeInfo create(String coreName) {
		try {
//			coreName = coreName.toLowerCase();
			Log.info("Pearl create node 1 : " + coreName);
			for(var type : deathShadowCoreTypes) {
				
				var typename = type.getSimpleName().toLowerCase();
				Log.info("Pearl create node 2 : " + coreName + " ?= " + typename);
				
				if(!coreName.equalsIgnoreCase(typename)) continue;
				
				Log.info("Pearl create node 2 : " + coreName + " == " + typename);
				
				var file = Environment.fromRoot("/release/deathshadows/" + coreName + ".jar").toFile();
				
				Process proc = null; //Runtime.getRuntime().exec("java -jar \"" + file.getAbsolutePath() + "\"");
				
				var node = new NodeInfo();
				node.id = tempIdmaker++;
				node.type = type;
				node.launchTime = System.currentTimeMillis();
				node.process = proc;
				nodes.add(node);

				Log.info("Pearl create node 3 : " + coreName + ", type " + type.getName());
				
				return node;
			}
		} catch (Exception e) {
			Log.error("", e);
		}
		return null;
	}
	
	public void getNextNodeID() {
		
	}

	
	
	
}
