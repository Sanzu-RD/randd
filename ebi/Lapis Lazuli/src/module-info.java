module com.souchy.randd.ebishoal.commons.LapisLazuli {
	//exports com.souchy.randd.ebishoal.commons.lapis.screens.monoscreens;
	exports com.souchy.randd.ebishoal.commons.lapis.ui;
	//exports com.souchy.randd.ebishoal.commons.lapis.screens;
	exports com.souchy.randd.ebishoal.commons.lapis.discoverers;
	exports com.souchy.randd.ebishoal.commons.lapis.drawing;
	exports com.souchy.randd.ebishoal.commons.lapis.main;
	exports com.souchy.randd.ebishoal.commons.lapis.managers;
	exports com.souchy.randd.ebishoal.commons.lapis.world;
	exports com.souchy.randd.ebishoal.commons.lapis.screen;
	
	requires transitive com.souchy.randd.commons.TealWaters;
	requires transitive com.souchy.randd.ebishoal.commons.EbiCore;
	requires transitive gdx;
	requires transitive gdx.backend.lwjgl;
	requires transitive java.desktop;
	requires transitive vis.ui;
	requires transitive gdx.lml;
	requires transitive gdx.lml.vis;
	requires transitive lwjgl.util;
	requires transitive GameMechanics2;
	requires transitive gson;
	
}