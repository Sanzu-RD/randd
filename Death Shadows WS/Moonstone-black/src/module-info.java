module com.souchy.randd.moonstone.Black {
	exports com.souchy.randd.deathshadows.blackmoonstone.main;
	exports com.souchy.randd.deathshadows.blackmoonstone.handlers;
	exports com.souchy.randd.deathshadows.blackmoonstone.riverhandlers;
	
	requires transitive CreatureCommons;
	requires transitive GameMechanics2;
	requires com.google.common;
	requires com.souchy.randd.commons.Jade;
	requires com.souchy.randd.commons.TealNet;
	requires com.souchy.randd.commons.TealWaters;
	requires com.souchy.randd.commons.deathebi;
	requires transitive com.souchy.randd.deathshadow.core;
	requires com.souchy.randd.deathshadows.iolite;
	requires com.souchy.randd.deathshadows.nodes.pearl.PearlMessaging;
	requires com.souchy.randd.moonstone.Commons;
	requires netty.all;
	requires org.mongodb.bson;
	requires MapIO;
}