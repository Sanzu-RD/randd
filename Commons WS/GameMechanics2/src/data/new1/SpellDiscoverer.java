package data.new1;

import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer;

public class SpellDiscoverer extends ClassDiscoverer<SpellModel> {

	@Override
	public boolean identify(Class<?> c) {
		return c != SpellModel.class && SpellModel.class.isAssignableFrom(c);
	}
	
}
