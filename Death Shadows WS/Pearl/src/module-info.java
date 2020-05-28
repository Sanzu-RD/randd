module com.souchy.randd.deathshadows.Pearl {
	exports com.souchy.randd.deathshadows.nodes.pearl;
	exports com.souchy.randd.deathshadows.nodes.pearl.handlers;
	
	requires com.souchy.randd.commons.TealNet;
	requires com.souchy.randd.commons.TealWaters;
//	requires com.souchy.randd.deathshadows.commons.Core;
	requires com.souchy.randd.deathshadows.nodes.pearl.PearlMessaging;
	requires com.souchy.randd.pluginprototyping.Modules;
	requires netty.all;
	requires com.souchy.randd.deathshadows.iolite;
	requires com.souchy.randd.deathshadow.core;
}