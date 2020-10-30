/**
 * 
 */
/**
 * @author Souchy
 *
 */
module com.souchy.randd.deathshadows.nodes.pearl.PearlMessaging {
	exports com.souchy.randd.deathshadows.pearl;
	
	exports com.souchy.randd.deathshadows.nodes.pearl.messaging;
//	exports com.souchy.randd.deathshadows.nodes.pearl.messaging.rivers;
//	exports com.souchy.randd.deathshadows.nodes.pearl.messaging.coral;
	exports com.souchy.randd.deathshadows.nodes.pearl.messaging.moonstone;
//	exports com.souchy.randd.deathshadows.nodes.pearl.messaging.opal;

	requires transitive com.souchy.randd.commons.TealWaters;
	requires transitive com.souchy.randd.AnnotationProcessor;
	requires transitive com.souchy.randd.commons.TealNet;
	requires transitive netty.all;
	requires transitive GameMechanics2;
	requires transitive com.souchy.randd.deathshadow.core;
}