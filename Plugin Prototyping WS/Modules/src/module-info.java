/**
 * 
 */
/**
 * @author Souchy
 *
 */
module com.souchy.randd.pluginprototyping.Modules {
	//exports com.souchy.randd.modules.node;
	exports com.souchy.randd.modules.api;
	exports com.souchy.randd.modules.node;
	//exports com.souchy.randd.modules.base;
	
	requires transitive gson;
	requires transitive com.souchy.randd.commons.TealWaters;
	requires com.google.common;
}