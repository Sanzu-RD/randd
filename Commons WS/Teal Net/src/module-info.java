module com.souchy.randd.commons.TealNet {
	exports com.souchy.randd.commons.net.netty.bytebuf.multihandlers;
	exports com.souchy.randd.commons.net.netty.bytebuf.pipehandlers;
	exports com.souchy.randd.commons.net.netty.client;
	exports com.souchy.randd.commons.net;
	exports com.souchy.randd.commons.net.netty.server;
	exports com.souchy.randd.commons.net.netty.bytebuf;
	
	requires com.souchy.randd.commons.TealWaters;
	requires netty.all;
}