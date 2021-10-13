package com.souchy.randd.commons.tealwaters.commons;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.commons.tealwaters.logging.Log.LogImportance;

public class Profiler {
	
	public long start = 0;
	
	public LogImportance level = LogImportance.Info;
	
	public Profiler() {
		start = System.currentTimeMillis();
	}
	public Profiler(LogImportance importance) {
		this();
		this.level = importance;
	}
	
	public long pop() {
		var t = System.currentTimeMillis() - start;
		start = System.currentTimeMillis();
		return t;
	}

	public void poll() {
		poll("");
	}
	public void poll(String str) {
		str += " ";
		Log.log(level, String.format("Profiler %s%sms", str, pop()));
	}
	
}
