/**
 * 
 */
/**
 * @author Souchy
 *
 */
module com.souchy.randd.moonstone.Commons {
	exports com.souchy.randd.moonstone.commons.packets;
	exports com.souchy.randd.moonstone.commons.packets.c2s;
	exports com.souchy.randd.moonstone.commons.packets.s2c;

	requires transitive netty.all;
	requires transitive GameMechanics2;
	requires transitive com.souchy.randd.commons.TealWaters;
	requires transitive com.souchy.randd.commons.TealNet;
	requires transitive com.souchy.randd.AnnotationProcessor;
}