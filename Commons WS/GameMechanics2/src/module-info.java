module GameMechanics2 {
	exports gamemechanics.common;
	exports gamemechanics.data.status;
	exports gamemechanics.data.spells;
	exports gamemechanics.data.effects.status;
	exports gamemechanics.data.effects.damage;
	exports gamemechanics.data.effects.displacement;
	exports gamemechanics.data.effects.other;
	exports gamemechanics.events;
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
	exports data.new1.timed;
	exports data.modules;
	
	requires transitive com.google.common;
	requires com.souchy.randd.commons.TealWaters;
	requires com.google.guice;
	requires com.souchy.randd.commons.Jade;
	requires com.souchy.randd.pluginprototyping.Modules;
}