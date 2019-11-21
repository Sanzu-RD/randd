package gamemechanics.statics;

import java.util.ArrayList;
import java.util.List;

import gamemechanics.statics.stats.properties.StatProperty;

public interface Element extends StatProperty {
	
	public static List<Element> values = new ArrayList<>();
	
	public static Element global = new Element() {
		{
			StatPropertyID.register(this);
		}
		public String name() {
			return "global";
		}
	};
	
	public String name();
	
}
