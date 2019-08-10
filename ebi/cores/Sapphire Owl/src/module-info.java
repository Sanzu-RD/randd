module com.souchy.randd.ebishoal.SapphireOwl {
	exports com.souchy.randd.ebishoal.sapphire.main;
	exports com.souchy.randd.ebishoal.sapphire.gfx;
	exports com.souchy.randd.ebishoal.sapphire.gfx.ui.roundImage;
	exports com.souchy.randd.ebishoal.sapphire.controls;

	requires GameMechanics2;
	requires com.souchy.randd.commons.TealNet;
	requires com.souchy.randd.commons.TealWaters;
	requires com.souchy.randd.ebishoal.commons.EbiCore;
	requires com.souchy.randd.ebishoal.commons.LapisLazuli;
	requires com.souchy.randd.pluginprototyping.Modules;
	requires com.souchy.randd.commons.Jade;
	
	requires netty.all;
	requires com.google.common;
	requires com.google.guice;
	requires gson;
	requires org.mongodb.bson;
	
	requires gdx;
	//requires gdx.kiwi;
	//requires gdx.lml;
	requires gdx.lml.vis;
	requires gdx.backend.lwjgl;
	//requires lwjgl.util;
	//requires vis.ui;

	//requires java.desktop;
	//requires java.sql;

	requires com.souchy.randd.AnnotationProcessor;
	requires reflections;
	
}