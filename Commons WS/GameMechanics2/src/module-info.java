module GameMechanics2 {
	exports data.new1;
//	exports gamemechanics.data;
	exports com.souchy.randd.commons.diamond.main;
	exports com.souchy.randd.commons.diamond.common;
	exports com.souchy.randd.commons.diamond.common.generic;
	exports com.souchy.randd.commons.diamond.ext;
	exports com.souchy.randd.commons.diamond.systems;
	
	exports com.souchy.randd.commons.diamond.models;
	exports com.souchy.randd.commons.diamond.models.stats;
	exports com.souchy.randd.commons.diamond.models.stats.special;
	exports com.souchy.randd.commons.diamond.models.stats.base;
	exports com.souchy.randd.commons.diamond.models.stats.maps;
	exports com.souchy.randd.commons.diamond.models.components;
	
	exports com.souchy.randd.commons.diamond.statics;
	exports com.souchy.randd.commons.diamond.statics.filters;
	exports com.souchy.randd.commons.diamond.statics.properties;
	exports com.souchy.randd.commons.diamond.statics.stats.properties;
	exports com.souchy.randd.commons.diamond.statics.stats.properties.spells;
	
	exports com.souchy.randd.commons.diamond.statusevents;
	exports com.souchy.randd.commons.diamond.statusevents.damage;
	exports com.souchy.randd.commons.diamond.statusevents.other;
	exports com.souchy.randd.commons.diamond.statusevents.status;
	exports com.souchy.randd.commons.diamond.statusevents.resource;
	exports com.souchy.randd.commons.diamond.statusevents.displacement;
	
	exports com.souchy.randd.commons.diamond.effects;
	exports com.souchy.randd.commons.diamond.effects.damage;
	exports com.souchy.randd.commons.diamond.effects.other;
	exports com.souchy.randd.commons.diamond.effects.status;
	exports com.souchy.randd.commons.diamond.effects.resources;
	exports com.souchy.randd.commons.diamond.effects.displacement;
	
	opens com.souchy.randd.commons.diamond.ext;
	
	
	requires transitive netty.all;
	requires transitive com.google.common;
	requires transitive com.souchy.randd.commons.TealWaters;
	requires transitive com.google.guice;
	requires transitive com.souchy.randd.commons.Jade;
	requires transitive com.google.gson;
	requires transitive com.souchy.randd.commons.TealNet;
	requires java.base;
	requires MapIO;
	requires org.mongodb.bson;
	requires RedDiamond;
}