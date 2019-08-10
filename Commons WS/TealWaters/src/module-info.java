module com.souchy.randd.commons.TealWaters {
	exports com.souchy.randd.commons.tealwaters.logging;
	exports com.souchy.randd.commons.tealwaters.properties;
	exports com.souchy.randd.commons.tealwaters.commons;
	exports com.souchy.randd.commons.tealwaters.io.files;
	
	requires transitive com.souchy.randd.AnnotationProcessor;
	requires transitive java.sql;
	requires transitive java.desktop;
	requires transitive gson;
	requires reflections;
	requires com.google.common;
}