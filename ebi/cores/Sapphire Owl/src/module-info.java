module com.souchy.randd.ebishoal.SapphireOwl {
	exports com.souchy.randd.ebishoal.sapphire.main;
	exports com.souchy.randd.ebishoal.sapphire.models;
	exports com.souchy.randd.ebishoal.sapphire.ui;
	exports com.souchy.randd.ebishoal.sapphire.ui.roundImage;
	
	requires transitive GameMechanics2;
	requires transitive com.google.common;
	requires transitive com.google.guice;
	requires transitive com.souchy.randd.commons.TealWaters;
	requires transitive com.souchy.randd.ebishoal.commons.EbiCore;
	requires transitive com.souchy.randd.ebishoal.commons.LapisLazuli;
	requires transitive gdx;
	requires transitive gdx.kiwi;
	requires transitive gdx.lml;
	requires transitive gson;
	requires vis.ui;
}