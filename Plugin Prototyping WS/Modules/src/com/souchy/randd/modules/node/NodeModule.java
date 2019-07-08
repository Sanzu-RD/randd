package com.souchy.randd.modules.node;

import com.souchy.randd.modules.api.Module;
import com.souchy.randd.modules.api.ModuleClassLoader;

public abstract class NodeModule implements Module<NodeInformation> {
	
	/**
	 * The class loader for this module. It will be closed on dispose();
	 */
	private ModuleClassLoader moduleClassloader;
	
	
	/*
	 * Ask a module to update itself without reloading it entirely (ex: amethyst reloads fxml scenes)
	 */
	public abstract void askUpdate(); 
	
	
	public ModuleClassLoader getClassLoader() {
		return moduleClassloader;
	}
	public void setClassLoader(ModuleClassLoader moduleClassloader) {
		this.moduleClassloader = moduleClassloader;
	}
	
}
