module GameMechanics2 {
	exports gamemechanics.common;
	exports gamemechanics.creatures;
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
	exports gamemechanics.properties;
	exports gamemechanics.stats;
	exports gamemechanics.status;
	//exports data;
	exports data.new1;
	exports data.modules;
	
	requires transitive com.google.common;
	requires com.souchy.randd.commons.TealWaters;
	requires com.google.guice;
	requires com.souchy.randd.commons.Jade;
	requires gdx;
	requires com.souchy.randd.pluginprototyping.Modules;
	requires gdx.lml;
}