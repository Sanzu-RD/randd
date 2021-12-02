/**
 * 
 */
/**
 * @author Souchy
 *
 */
module com.souchy.randd.pluginprototyping.Modules {
	exports com.souchy.randd.modules.api;
	exports com.souchy.randd.modules.node;
	exports com.souchy.randd.modules.base;
	
	requires transitive com.google.gson;
	requires transitive com.souchy.randd.commons.TealWaters;
	requires transitive com.google.common;
}