/**
 * 
 */
/**
 * @author Souchy
 *
 */
module com.souchy.randd.moonstone.Black {
	exports com.souchy.randd.deathshadows.blackmoonstone.main;
	exports com.souchy.randd.deathshadows.blackmoonstone.handlers;

	requires transitive com.souchy.randd.moonstone.Commons;

	requires transitive com.souchy.randd.commons.TealNet;
	requires transitive com.souchy.randd.AnnotationProcessor;
	requires transitive com.souchy.randd.commons.TealWaters;
	
	requires transitive com.souchy.randd.deathshadow.core;
	
	requires transitive netty.all;
	requires transitive com.rabbitmq.client;
	requires transitive com.google.common;
	requires transitive com.google.gson;
	requires GameMechanics2;
	requires com.souchy.randd.deathshadows.nodes.pearl.PearlMessaging;
	requires CreatureCommons;
//	requires com.souchy.randd.deathshadows.commons.deathrivers;
}