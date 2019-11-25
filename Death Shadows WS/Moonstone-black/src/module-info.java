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

	requires com.souchy.randd.commons.TealNet;
	requires com.souchy.randd.AnnotationProcessor;
	requires com.souchy.randd.commons.TealWaters;
	
	requires com.rabbitmq.client;
	requires netty.all;
	requires com.souchy.randd.deathshadows.commons.Core;
	requires com.google.common;
}