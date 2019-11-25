/**
 * 
 */
/**
 * @author Souchy
 *
 */
module com.souchy.randd.deathshadows.nodes.pearl.PearlMessaging {
	exports com.souchy.randd.deathshadows.nodes.pearl.messaging;

	requires com.souchy.randd.commons.TealWaters;
	requires com.souchy.randd.AnnotationProcessor;
	requires com.souchy.randd.commons.TealNet;
	requires transitive netty.all;
}