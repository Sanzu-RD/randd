module com.souchy.randd.commons.coral {
	exports com.souchy.randd.commons.coral.out;
	exports com.souchy.randd.commons.coral.in;
	
	requires transitive com.souchy.randd.commons.TealNet;
	requires transitive com.souchy.randd.commons.TealWaters;
	requires transitive netty.all;
	requires transitive com.souchy.randd.commons.Jade;
	requires GameMechanics2;
	requires com.souchy.randd.AnnotationProcessor;
}