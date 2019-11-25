module com.souchy.randd.ebishoal.opaline {
	exports com.souchy.randd.ebishoal.opaline.api;
	
	requires transitive com.souchy.randd.ebishoal.commons.EbiCore;
	
	requires transitive java.ws.rs;
	requires transitive java.json.bind;
	requires transitive com.souchy.randd.commons.Jade;
	requires transitive com.souchy.randd.commons.TealWaters;
	requires transitive jersey.client;
	
	requires transitive com.souchy.randd.commons.opal;
}