/**
 * 
 */
/**
 * @author Souchy
 *
 */
module com.souchy.randd.pluginprototyping.MyDummyPlugin {
	exports com.souchy.randd.modules.mydummyplugin;

	requires transitive com.souchy.randd.pluginprototyping.Modules;
	
	requires com.souchy.randd.pluginprototyping.PluginMonitor;
}