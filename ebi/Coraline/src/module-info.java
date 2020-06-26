module com.souchy.randd.ebishoal.coraline {
	exports com.souchy.randd.ebishoal.coraline;
	exports com.souchy.randd.ebishoal.coraline.handlers.matchmaking;
	exports com.souchy.randd.ebishoal.coraline.handlers.auth;
	
	requires com.souchy.randd.commons.Jade;
	requires com.souchy.randd.commons.TealNet;
	requires com.souchy.randd.commons.TealWaters;
	requires com.souchy.randd.commons.coral;
	requires com.souchy.randd.commons.deathebi;
	requires com.souchy.randd.ebishoal.commons.EbiCore;
	requires netty.all;
}