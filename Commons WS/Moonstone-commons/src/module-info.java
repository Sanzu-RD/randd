/**
 * 
 */
/**
 * @author Souchy
 *
 */
module com.souchy.randd.moonstone.Commons {
	exports com.souchy.randd.moonstone.commons.packets.c2s;
	exports com.souchy.randd.moonstone.commons.packets.s2c;

	requires com.souchy.randd.commons.TealWaters;
	requires com.souchy.randd.commons.TealNet;
	requires com.souchy.randd.AnnotationProcessor;
	requires netty.all;
	requires GameMechanics2;
}