module com.souchy.randd.commons.coral {
	exports com.souchy.randd.commons.coral.out;
	exports com.souchy.randd.commons.coral.draft;
	exports com.souchy.randd.commons.coral.in;
	
	requires GameMechanics2;
	requires com.souchy.randd.AnnotationProcessor;
	requires com.souchy.randd.commons.Jade;
	requires com.souchy.randd.commons.TealNet;
	requires com.souchy.randd.commons.TealWaters;
	requires netty.all;
}