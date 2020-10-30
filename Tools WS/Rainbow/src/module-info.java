module com.souchy.randd.tools.Rainbow {
	exports com.souchy.randd.tools.rainbow.main;
	exports com.souchy.randd.tools.rainbow.ui;
	exports com.souchy.randd.tools.rainbow.handlers;
//	exports com.souchy.randd.tools.rainbow.acknowledgers;

	opens com.souchy.randd.tools.rainbow.main;
	opens com.souchy.randd.tools.rainbow.ui;
	
	requires transitive com.souchy.randd.deathshadows.nodes.pearl.PearlMessaging;
	requires transitive com.souchy.randd.commons.TealNet;
	requires transitive com.souchy.randd.commons.TealWaters;
	requires transitive com.souchy.randd.ebishoal.commons.EbiCore;
	requires transitive java.desktop;
	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;
	requires com.souchy.randd.commons.deathebi;
	requires com.google.common;
	requires javafx.web;
	requires com.souchy.randd.moonstone.Black;
	requires com.souchy.randd.deathshadows.coral;
	requires com.souchy.randd.deathshadows.opal;
//	requires com.souchy.randd.deathshadows.greenberyl;
}