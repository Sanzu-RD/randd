package com.souchy.randd.commons.tealwaters.logging;

import java.time.ZonedDateTime;

public class Log {
	
	public static enum LogImportance {
		// 1,     2,     4,     8,       16,     32
		Debug, Verbose, Info, Warning, Error, Critical;
		public byte pow() {
			return (byte) Math.pow(2, ordinal());
		}
		/** total = 63 */
		public static int filter = Debug.pow() | Verbose.pow() | Info.pow() | Warning.pow() | Error.pow() | Critical.pow();
	}
	
	public ZonedDateTime date; // Instant
	public LogImportance importance;
	public String module;
	public String details;
	
	public Log(LogImportance importance, String details) {
		this.importance = importance;
		this.details = details;
		this.module = String.valueOf(Logging.rootModule); //.getName();
		this.date = ZonedDateTime.now(); // Instant.now(Clock.system(ZoneId.));
	}
	
	@Override
	public String toString() {
		return "[" + date + "]\t[" + module + "]\t[" + importance + "] : " + details;
	}
	
	public static class DefferedLog extends Log {
		public String process;
		public DefferedLog(String process, String details) {
			super(LogImportance.Info, details);
			this.process = process;
		}

		@Override
		public String toString() {
			return process + ":// " + details;
		}
	}
	
	public static void log(LogImportance importance, String details) {
		Logging.log(new Log(importance, details));
	}
	

	public static void debug(String details) {
		Logging.log(new Log(LogImportance.Debug, details));
	}
	public static void debug(String details, Object... elements) {
		debug(String.format(details, elements));
	}
	
	public static void verbose(String details) {
		Logging.log(new Log(LogImportance.Verbose, details));
	}
	public static void verbose(String details, Object... elements) {
		verbose(String.format(details, elements));
	}
	
	public static void info(String details) {
		Logging.log(new Log(LogImportance.Info, details));
	}
	
//	public static void info(String details,  String... elements) {
//		info(details + " { " + String.join(", ", elements) + " }");
//	}

	public static void info(String details, Object... elements) {
		info(String.format(details, elements));
	}
	
	public static void format(String details, Object... elements) {
		info(String.format(details, elements));
	}
	
	public static void info(String details,  Iterable<? extends CharSequence> elements) {
		info(details + " { " + String.join(", ", elements) + " }");
	}
	
	public static void info(String details, Throwable e) {
		info(details + "\n" + throwableToString(e));
	}
	
	public static void warning(String details) {
		Logging.log(new Log(LogImportance.Warning, details));
	}
	
	public static void warning(String details, Throwable e) {
		warning(details + "\n" + throwableToString(e));
	}
	
	public static void error(String details) {
		Logging.log(new Log(LogImportance.Error, details));
	}

	public static void error(String details, Object... elements) {
		error(String.format(details, elements));
	}
	
	public static void error(String details, Throwable e) {
		error(details + "\n" + throwableToString(e));
	}
	
	public static void critical(String details) {
		Logging.log(new Log(LogImportance.Critical, details));
	}
	
	public static void critical(String details, Throwable e) {
		critical(details + "\n" + throwableToString(e));
	}

	public static void deffered(String module, String details) {
		Logging.log(new DefferedLog(module, details));
	}
	public static void defferedError(String module, String details) {
		var log = new DefferedLog(module, details);
		log.importance = LogImportance.Error;
		Logging.log(log);
	}
	
	private static String throwableToString(Throwable e) {
		StringBuilder stackTrace = new StringBuilder();
		
		appendStackTrace(stackTrace, e, "", null);
		
		// Print suppressed exceptions, if any
		for (Throwable t : e.getSuppressed()) {
			appendStackTrace(stackTrace, t, "\n\tSuppressed: ",e.getStackTrace());
		}
		// System.out.println("cause = " + e.getCause());
		// Print cause, if any
		if(e.getCause() != null) {
			appendStackTrace(stackTrace, e.getCause(), "\nCaused by: ", e.getStackTrace());
		}
		
		return /* "\n" + e.getLocalizedMessage() + */stackTrace.toString();
	}
	
	/**
	 * equivalent to {@link java.lang.Throwable#printEnclosedStackTrace}
	 */
	private static void appendStackTrace(StringBuilder s, Throwable e, String caption, StackTraceElement[] enclosingTrace) {

        s.append(caption + e.toString());
        
		// Compute number of frames in common between this and enclosing trace
        int framesInCommon = 0;
        int m = e.getStackTrace().length - 1;
		if(enclosingTrace != null) {
			int n = enclosingTrace.length - 1;
	        while (m >= 0 && n >=0 && e.getStackTrace()[m].equals(enclosingTrace[n])) {
	            m--; n--;
	        }
	        framesInCommon = e.getStackTrace().length - 1 - m;
        }
        
		for (int i = 0; i <= m; i++) {
			s.append("\n\tat " + e.getStackTrace()[i]);
		}
		if(framesInCommon > 0)
			s.append("\n\t ... " + framesInCommon + " more");
	}
	
}
