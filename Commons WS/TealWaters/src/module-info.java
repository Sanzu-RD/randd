module com.souchy.randd.commons.TealWaters {
	exports com.souchy.randd.commons.tealwaters.logging;
	exports com.souchy.randd.commons.tealwaters.properties;
	exports com.souchy.randd.commons.tealwaters.commons;
	exports com.souchy.randd.commons.tealwaters.io.files;
	exports com.souchy.randd.commons.tealwaters.data.tables;
	exports com.souchy.randd.commons.tealwaters.ecs;
	
	requires transitive com.souchy.randd.AnnotationProcessor;
	requires transitive java.sql;
	requires transitive java.desktop;
	requires transitive com.google.gson;
	requires transitive reflections;
	requires transitive com.google.common;
	requires org.mongodb.bson;
	
}