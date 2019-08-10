module com.souchy.randd.ebishoal.commons.LapisLazuli {
	exports com.souchy.randd.ebishoal.commons.lapis.gfx.ui;
	exports com.souchy.randd.ebishoal.commons.lapis.gfx.screen;
	exports com.souchy.randd.ebishoal.commons.lapis.gfx.shadows;
	exports com.souchy.randd.ebishoal.commons.lapis.discoverers;
	exports com.souchy.randd.ebishoal.commons.lapis.lining;
	exports com.souchy.randd.ebishoal.commons.lapis.main;
	exports com.souchy.randd.ebishoal.commons.lapis.world;

	requires transitive GameMechanics2;
	requires transitive com.souchy.randd.commons.TealNet;
	requires transitive com.souchy.randd.commons.TealWaters;
	requires com.souchy.randd.ebishoal.commons.EbiCore;
	requires transitive com.souchy.randd.pluginprototyping.Modules;
	
	requires transitive netty.all;
	requires transitive gdx;
	requires transitive gdx.backend.lwjgl;
	requires transitive java.desktop;
	requires transitive vis.ui;
	requires transitive gdx.lml;
	requires transitive gdx.lml.vis;
//	requires transitive lwjgl.util;
	requires transitive gson;
	
}