package data;

import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer;

import data.new1.SpellModel;

public class SpellDiscoverer extends ClassDiscoverer<SpellModel> {

	@Override
	public boolean identify(Class<?> c) {
		return c != SpellModel.class && SpellModel.class.isAssignableFrom(c);
	}
	
}
