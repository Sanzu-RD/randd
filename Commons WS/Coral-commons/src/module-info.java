module com.souchy.randd.commons.coral {
	exports com.souchy.randd.commons.coral.out;
	exports com.souchy.randd.commons.coral.draft;
	exports com.souchy.randd.commons.coral.in;
	
	requires transitive GameMechanics2;
	requires transitive com.souchy.randd.AnnotationProcessor;
	requires transitive com.souchy.randd.commons.Jade;
	requires transitive com.souchy.randd.commons.TealNet;
	requires transitive com.souchy.randd.commons.TealWaters;
	requires transitive netty.all;
	requires transitive org.mongodb.bson;
}