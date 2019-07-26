package com.souchy.randd.ebishoal.octopusink;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.ebishoal.commons.EbiCore;
import com.souchy.randd.modules.api.EntryPoint;
import com.souchy.randd.modules.node.NodeManager;

public class Octopus extends EbiCore { // implements EntryPoint {

	public static OctopusConf conf;
	//public static NodeManager manager;
	
	public static void main(String[] args) throws Exception {
		launch(new Octopus());
	}

	@Override
	public void init() throws Exception {
		Log.info("Octopus init");
		conf = JsonConfig.readExternal(OctopusConf.class);
		//manager = new NodeManager(this);
	}

	@Override
	public void start() {
		Log.info("Octopus start");
		//update2();
		//Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(this::update, 0, 10, TimeUnit.SECONDS);
		
		// check if this directory is mostly empty??
		// create repository
		// or open repository
		// then fetch + pull update
		// then start amethyst
	}
	
	/**
	 * ok so we have 2 folders locally.
	 * one is the exact representation of the remote.
	 * one is the current build.
	 * so we compare every file between those two folders,
	 * build a list of added/modified/removed files
	 * then upload a patch from those
	 * and update the local representation of the remote
	 */
	/*
	public void patcher() {
		List<File> local = new ArrayList<>(); // get every file recursively
		List<File> remote = new ArrayList<>(); // get every file recursively
		var itor = local.iterator();
		
		Function<File, File> findFirst = f1 -> {
			File result = null;
			for(var f2 : remote) {
				try {
					if(f1.getCanonicalPath().equals(f2.getCanonicalPath())) {
						result = f2;
						break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return result;
		};
		
		List<File> modified = new ArrayList<>();
		List<File> added = new ArrayList<>();
		List<File> removed = new ArrayList<>();
		
		while(itor.hasNext()) {
			var localF = itor.next(); // 
			var remotF = findFirst.apply(localF);
			// if it's a new file
			if(remotF == null) {
				added.add(localF);
			}
			// if it exists but might be modified
			else {
				try {
					// if the sizes are different
					if(Files.size(localF.toPath()) != Files.size(remotF.toPath())) {
						modified.add(localF);
					}  
					// if the modified date is different
					else if(Files.getLastModifiedTime(localF.toPath()) != Files.getLastModifiedTime(localF.toPath())) {
						modified.add(localF);
					} else {
						//Files.newBufferedReader(localF.toPath());
				        int len = 20 * 1024 * 1024; // 20 MB
						var is1 = Files.newInputStream(localF.toPath());
						var is2 = Files.newInputStream(localF.toPath());
				        var fis1 = new BufferedInputStream(is1, len);
				        var fis2 = new BufferedInputStream(is2, len);
				        byte[] buf1 = new byte[0];
				        byte[] buf2 = new byte[0];
				        int equal = 0;
				        while(buf1.length > 0 && equal == 0) {
				        	buf1 = fis1.readNBytes(len);
					        buf2 = fis2.readNBytes(len);
					        equal = Arrays.compare(buf1, buf2);
				        }
				        fis1.close();
				        fis2.close();
				        if(equal != 0) {
				        	modified.add(localF);
				        }
					}
				} catch(Exception e) {
					modified.add(localF);
				}
			}
		}
		// if there's any remaining files on remote, that means they're deleted on local
		if(remote.size() > 0) {
			remote.forEach(f -> removed.add(f));
		}
	}
	
	
	
	public void update2() {
		// download to appdata/temp
	}*/
	
	
	/**
	 * realistically, this method should be in the modulemanager
	 * it should have a built-in reloading function for reload(info) and reloadAll() and maybe autoReload() or something having to do with new versions
	 */ 
	/*
	public void update() {
		Log.info("Octopus updating");
		try {
			// shutdown all threads
			manager.disposeExecutors(); //.getExecutors().shutdownNow();
			// dispose all current modules
			manager.disposeAll();
			// clear all infos
			manager.getInfos().clear();
			// discover new modules infos
			manager.explore(new File(conf.modulePath));
			// instantiate new modules
			manager.instantiateAll();
			Log.info("Octopus updated");
		} catch (Exception e) {
			Log.warning("Octopus update error : ", e);
		}
	}*/

	@Override
	protected String[] getRootPackages() {
		return new String[]{ "com.souchy.randd.ebishoal.octopusink" };
	}
	
	
}
