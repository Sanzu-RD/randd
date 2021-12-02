module com.souchy.randd.deathshadows.iolite {
	exports com.souchy.randd.deathshadows.iolite.emerald;
	exports com.souchy.randd.deathshadows.iolite.emerald.codecs;
	exports com.souchy.randd.deathshadows.iolite.ruby;
	exports com.souchy.randd.deathshadows.iolite;
	
	requires transitive com.souchy.randd.commons.Jade;
	requires transitive com.souchy.randd.commons.TealWaters;
	requires transitive org.mongodb.bson;
	requires transitive org.mongodb.driver.core;
	requires transitive org.mongodb.driver.sync.client;
	requires transitive jedis;
}