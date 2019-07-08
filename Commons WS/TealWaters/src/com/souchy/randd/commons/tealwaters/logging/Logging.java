package com.souchy.randd.commons.tealwaters.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.souchy.randd.commons.tealwaters.commons.Environment;
import com.souchy.randd.commons.tealwaters.io.files.JsonHelpers;
import com.souchy.randd.commons.tealwaters.io.files.JsonHelpers.InstantAdapter;
import com.souchy.randd.commons.tealwaters.io.files.JsonHelpers.ZonedDateTimeAdapter;
import com.souchy.randd.commons.tealwaters.logging.Log.LogImportance;


public final class Logging {
	
	static Module rootModule;
	
	public static final List<Consumer<Log>> streams = new ArrayList<>();
	
	private static final File currentFile;
	
	static {
		createDir();
		currentFile = createFile();
		createSysoutStream();
		createFileSteam();
	}

	// ---------------------------------------------------------------------------
	
	/**
	 * Used to get the name of the java module source of the logs. The name of the module will be in each log.
	 * @param m
	 */
	public static void registerLogModule(Class<?> m) {
		rootModule = m.getModule();
	}
	public static void log(Log log) {
		streams.forEach(s -> s.accept(log));
	}
	
	// ---------------------------------------------------------------------------
	
	private static void createDir() {
		try {
			if(!Files.exists(Environment.logs)) 
				Files.createDirectory(Environment.logs);
		} catch (IOException e1) { e1.printStackTrace(); }
	}
	private static File createFile() {
		var file = Environment.logs + "/" + Instant.now().toString().replaceFirst(":", "h").replaceFirst(":", "m").replaceFirst(":", "s") + ".log";
		return new File(file);
	}
	private static void createSysoutStream() {
		/** Sysout output stream */
		streams.add(l -> {
			var msg = "["+l.date+"]\t["+rootModule.getName()+"]\t[" + l.importance + "] : " + l.details;
			if(l.importance.ordinal() < LogImportance.Error.ordinal()) System.out.println(msg);
			else System.err.println(msg);
		});
	}
	private static void createFileSteam() {
		/** Gson serializer */
		Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeHierarchyAdapter(Instant.class, new InstantAdapter())
			.registerTypeHierarchyAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
			.setExclusionStrategies(JsonHelpers.exclusionStrategy)
			.create();
		
		/** File output stream */
		streams.add(l -> {
			try {
				String json = gson.toJson(l);
				PrintWriter pw = new PrintWriter(new FileWriter(currentFile, true));
				pw.println(json);
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	
}
