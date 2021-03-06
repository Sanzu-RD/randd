module com.souchy.randd.ebishoal.Amethyst {
	exports com.souchy.randd.ebishoal.amethyst.main;
	exports com.souchy.randd.ebishoal.amethyst.ui;
	exports com.souchy.randd.ebishoal.amethyst.ui.tabs;
	exports com.souchy.randd.ebishoal.amethyst.ui.components;
	
	opens com.souchy.randd.ebishoal.amethyst.ui;
	opens com.souchy.randd.ebishoal.amethyst.ui.tabs;
	opens com.souchy.randd.ebishoal.amethyst.ui.components;
	
	requires transitive com.souchy.randd.commons.Jade;
	requires transitive com.souchy.randd.commons.TealWaters;
	requires transitive com.souchy.randd.ebishoal.commons.EbiCore;
	requires transitive com.souchy.randd.commons.opal;
	requires transitive com.souchy.randd.ebishoal.opaline;
	requires transitive java.desktop;
	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;
	requires transitive com.souchy.randd.commons.deathebi;
	requires transitive CreatureCommons;
	requires transitive GameMechanics2;
	requires transitive com.souchy.randd.commons.coral;
	requires transitive com.souchy.randd.ebishoal.coraline;
	requires transitive com.google.common;
	
}