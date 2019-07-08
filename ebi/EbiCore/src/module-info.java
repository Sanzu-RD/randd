module com.souchy.randd.ebishoal.commons.EbiCore {
	exports com.souchy.randd.ebishoal.commons;
	
	requires transitive com.souchy.randd.commons.TealNet;
	requires transitive com.souchy.randd.commons.TealWaters;
	requires transitive netty.all;
	requires transitive com.souchy.randd.pluginprototyping.Modules;
}