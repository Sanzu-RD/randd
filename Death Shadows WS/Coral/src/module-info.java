module com.souchy.randd.deathshadows.coral {
	exports com.souchy.randd.deathshadows.coral;
	exports com.souchy.randd.deathshadows.coral.handlers;
	
	requires transitive com.souchy.randd.commons.Jade;
	requires transitive com.souchy.randd.deathshadows.iolite;
	requires transitive org.mongodb.bson;
	requires transitive org.mongodb.driver.core;
	requires transitive com.google.common;
	requires transitive com.souchy.randd.commons.TealNet;
	requires transitive com.souchy.randd.commons.coral;
	requires transitive netty.all;
	requires transitive com.souchy.randd.commons.deathebi;
	requires transitive org.mongodb.driver.sync.client;
	requires transitive com.souchy.randd.deathshadow.core;
}