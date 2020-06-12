package com.souchy.randd.deathshadows.nodes.pearl.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;
import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer.DefaultClassDiscoverer;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.deathshadow.core.DeathShadowCore;
//import com.souchy.randd.deathshadows.commons.core.CoreServer;
import com.souchy.randd.deathshadow.core.DeathShadowTCP;

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
	
	private String root = "../../../";
	private final List<File> jars = new ArrayList<>();
	private final List<NodeInfo> nodes = new ArrayList<>();
	
	public static void main(String[] args) throws Exception {
		core = new Pearl(args);
	}
	
	public Pearl(String[] args) throws Exception {
		super(args);
		int port = 1000;
		if(args.length > 0) port = Integer.parseInt(args[0]);
		if(args.length > 1) root = args[1];
		
		getCoreJars();
		create("blackmoonstone");
		
		// start server
		server = new DeathShadowTCP(port, this); 
		server.block(); 
	}


	@Override
	protected String[] getRootPackages() {
		return new String[]{ "com.souchy.randd.deathshadows.nodes.pearl" };
	}

	
	public void getCoreJars() {
		Environment.root = Paths.get(root);
		var deathShadowCoreList = new DefaultClassDiscoverer<DeathShadowCore>(DeathShadowCore.class).explore("com.souchy.randd.deathshadows");
		for(var core : deathShadowCoreList) {
			var file = Environment.fromRoot("release/deathshadows/" + core.getSimpleName().toLowerCase() + ".jar").toFile();
			if(file.exists()) {
				Log.info("death shadow core : " + file.getName());
				jars.add(file);
			}
		}
	}

	public void create(String coreName) {
		try {
			for (var jar : jars) {
				if(jar.getName() == coreName.toLowerCase() + ".jar") {
					Process proc = Runtime.getRuntime().exec("java -jar " + jar);
				}
			}
		} catch (IOException e) {
			Log.error("", e);
		}
	}
	
	public void getNextNodeID() {
		
	}

	
	
	
}
