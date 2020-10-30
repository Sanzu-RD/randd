module com.souchy.randd.deathshadows.Pearl {
	exports com.souchy.randd.deathshadows.pearl.main;
	exports com.souchy.randd.deathshadows.pearl.handlers;
	exports com.souchy.randd.deathshadows.pearl.rabbithandlers;
	
	
	
	requires transitive com.souchy.randd.deathshadows.opal;
	requires transitive com.souchy.randd.deathshadows.coral;
	requires transitive com.souchy.randd.moonstone.Black;
	
	requires transitive com.souchy.randd.commons.TealNet;
	requires transitive com.souchy.randd.commons.TealWaters;
//	requires transitive com.souchy.randd.deathshadows.commons.Core;
	requires transitive com.souchy.randd.deathshadows.nodes.pearl.PearlMessaging;
	requires transitive netty.all;
	requires transitive com.souchy.randd.deathshadows.iolite;
	requires transitive com.souchy.randd.deathshadow.core;
}