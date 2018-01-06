/**
 * 
 */
/**
 * @author Souchy
 *
 */
module com.souchy.randd.pluginprototyping.PluginMonitor {
	exports com.souchy.randd.modules.monitor.io;
	exports com.souchy.randd.modules.monitor.ui;
	exports com.souchy.randd.modules.monitor.main;
	opens com.souchy.randd.modules.monitor.ui to javafx.fxml;
	
	requires transitive com.hiddenpiranha.commons.TealWaters;
	requires transitive com.souchy.randd.pluginprototyping.Modules;
	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;
}