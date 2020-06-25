module com.souchy.randd.commons.Jade {
	exports com.souchy.randd.jade.meta;
	exports com.souchy.randd.jade.matchmaking;
	//exports com.souchy.randd.jade.combat;
	//exports com.souchy.randd.jade.oldcombat;
	
	requires transitive com.google.common;
	requires transitive com.souchy.randd.commons.TealWaters;
	requires transitive org.mongodb.bson;
	requires transitive netty.all;
	requires transitive com.souchy.randd.commons.TealNet;
}