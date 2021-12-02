package com.souchy.randd.commons.diamond.models.stats.maps;

import java.util.HashMap;

import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;

public class ResourceMap extends HashMap<String, IntStat> {
	private static final long serialVersionUID = -4703716554211161452L;
	public IntStat get(Resource k) {
		return super.get(k.name());
	}
	public IntStat put(Resource key, IntStat value) {
		return super.put(key.name(), value);
	}
}