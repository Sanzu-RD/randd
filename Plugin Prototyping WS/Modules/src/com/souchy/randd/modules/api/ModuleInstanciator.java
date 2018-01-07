package com.souchy.randd.modules.api;

/**
 * Called ModuleLoader before
 * @author Souchy
 *
 */
public interface ModuleInstanciator<M extends Module, I extends ModuleInformation> {

	/**
	 * Called load(..) before
	 * @param info
	 * @return
	 */
	public M instanciate(I info);

	/**
	 * Called load(..) before
	 * @param info
	 * @param destination
	 */
	//public void instanciate(I info, ModuleManager destination);
	
}
