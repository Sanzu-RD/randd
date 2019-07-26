package data;

import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer;

public class SpellDiscoverer extends ClassDiscoverer<SpellData> {

	@Override
	public boolean identify(Class<?> c) {
		return c != SpellData.class && SpellData.class.isAssignableFrom(c);
	}
	
}
