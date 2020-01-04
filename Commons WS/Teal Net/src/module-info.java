module com.souchy.randd.commons.TealNet {
	exports com.souchy.randd.commons.net;
	//exports com.souchy.randd.commons.net.netty;
	exports com.souchy.randd.commons.net.netty.client;
	exports com.souchy.randd.commons.net.netty.server;
	exports com.souchy.randd.commons.net.netty.bytebuf;
	exports com.souchy.randd.commons.net.netty.bytebuf.multihandlers;
	exports com.souchy.randd.commons.net.netty.bytebuf.pipehandlers;
	exports com.souchy.randd.commons.net.netty.bytebuf.reflect;
	
	requires com.souchy.randd.commons.TealWaters;
	requires transitive netty.all;
	requires com.souchy.randd.AnnotationProcessor;
	requires jersey.container.netty.http;
	requires jersey.server;
}