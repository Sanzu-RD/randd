/**
 * 
 */
/**
 * @author Souchy
 *
 */
module com.souchy.randd.deathshadows.nodes.pearl.PearlMessaging {
	exports com.souchy.randd.deathshadows.nodes.pearl.messaging;

	requires transitive com.souchy.randd.commons.TealWaters;
	requires transitive com.souchy.randd.AnnotationProcessor;
	requires transitive com.souchy.randd.commons.TealNet;
	requires transitive netty.all;
}