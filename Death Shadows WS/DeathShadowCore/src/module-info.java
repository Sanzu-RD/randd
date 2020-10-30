module com.souchy.randd.deathshadow.core {
	exports com.souchy.randd.deathshadow.core;
	exports com.souchy.randd.deathshadow.core.handlers;
	exports com.souchy.randd.deathshadow.core.smoothrivers;
	
	requires transitive com.souchy.randd.commons.TealNet;
	requires transitive netty.all;
	requires transitive com.souchy.randd.commons.Jade;
	requires transitive com.souchy.randd.commons.deathebi;
	requires transitive com.souchy.randd.deathshadows.iolite;
	requires com.souchy.randd.commons.TealWaters;
	requires com.google.common;
	requires com.rabbitmq.client;
	requires java.management;
	requires jdk.management;
}