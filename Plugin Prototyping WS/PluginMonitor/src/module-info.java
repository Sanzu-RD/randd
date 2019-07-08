module com.souchy.randd.pluginprototyping.PluginMonitor {
	exports com.souchy.randd.modules.monitor.io;
	exports com.souchy.randd.modules.monitor.ui;
	exports com.souchy.randd.modules.monitor.modules;
	exports com.souchy.randd.modules.monitor.main;
	
	requires com.souchy.randd.commons.TealWaters;
	requires com.souchy.randd.pluginprototyping.Modules;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
}