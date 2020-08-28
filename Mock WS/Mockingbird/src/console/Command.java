package console;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Command {
	
	public static Map<String, Consumer<Command>> consumers = new HashMap<>();
	
	public static DateFormat format = DateFormat.getDateInstance();
	
	
	public Date date;
	
	/**
	 * example "packet"
	 * if you want to set the channel to not have to type the prefix again, type "/packet"
	 */
	public String prefix;
	
	/**
	 * arguments
	 */
	public String data;
	
	public String toString() {
		return "[" + format.format(date) + "] [" + prefix + "] :" + data;
	}
	
}
