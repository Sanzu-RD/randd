module GameMechanics2 {
	exports gamemechanics.common;
	exports gamemechanics.common.generic;
	exports gamemechanics.data.effects.status;
	exports gamemechanics.data.effects.damage;
	exports gamemechanics.data.effects.displacement;
	exports gamemechanics.data.effects.other;
//	exports gamemechanics.events;
	exports gamemechanics.events.new1;
	exports gamemechanics.ext;
	exports gamemechanics.models;
	exports gamemechanics.models.entities;
	//exports gamemechanics.statics.creatures;
	exports gamemechanics.statics;
	exports gamemechanics.statics.filters;
	exports gamemechanics.statics.properties;
	exports gamemechanics.statics.stats;
	exports gamemechanics.statics.stats.properties;
	exports gamemechanics.statics.stats.modifiers;
	//exports gamemechanics.status;
	//exports data;
	exports data.new1;
	exports data.new1.spellstats;
	exports data.new1.timed;
	exports data.new1.spellstats.base;
//	exports data.modules;
	
	requires transitive com.google.common;
	requires com.souchy.randd.commons.TealWaters;
	requires com.google.guice;
	requires com.souchy.randd.commons.Jade;
	requires com.souchy.randd.pluginprototyping.Modules;
}