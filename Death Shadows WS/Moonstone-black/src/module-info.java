/**
 * 
 */
/**
 * @author Souchy
 *
 */
module com.souchy.randd.moonstone.Black {
	exports com.souchy.randd.deathshadows.nodes.moonstone.black;
	exports com.souchy.randd.deathshadows.nodes.moonstone.black.handlers;

	requires transitive com.souchy.randd.moonstone.Commons;

	requires transitive com.souchy.randd.commons.TealNet;
	requires transitive com.souchy.randd.AnnotationProcessor;
	requires transitive com.souchy.randd.commons.TealWaters;
	
	requires transitive com.rabbitmq.client;
	requires transitive netty.all;
	requires transitive com.google.common;
	requires transitive com.souchy.randd.deathshadow.core;
}