package com.souchy.randd.commons.diamond.models.stats.maps;

import java.util.HashMap;

import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.statics.Element;

public class ElementMap extends HashMap<String, IntStat> {
	private static final long serialVersionUID = -7665062264352159684L;
	public IntStat get(Element k) {
		return super.get(k.name());
	}
	public IntStat put(Element key, IntStat value) {
		return super.put(key.name(), value);
	}
}