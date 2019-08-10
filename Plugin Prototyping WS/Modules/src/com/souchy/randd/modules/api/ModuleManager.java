package com.souchy.randd.modules.api;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.souchy.randd.commons.tealwaters.logging.Log;

public interface ModuleManager<M extends Module<I>, I extends ModuleInformation> {

	/**
	 * Point d'accès donné aux modules
	 */
	public EntryPoint getEntry();
	/**
	 * Information loader, finds jars and reads their info.properties file.
	 */
	public ModuleDiscoverer getDiscoverer();
	/**
	 * Module loader, creates a modclassloader and loads the modules's mainclass into it.
	 */
	public ModuleInstantiator<M, I> getModuleloader();
	/**
	 * Extracts module information from a module jar.
	 */
	public ModuleInformationSupplier<I> getInformationSupplier();
	/**
	 * Lists jars informations, indexed by file name.
	 */
	public Map<String, I> getModuleInfos();
	/**
	 * Instantiated modules.
	 */
	public Map<String, M> getModules();
	
	public ExecutorService getExecutors();
	
	/**
	 * Clears all modules informations, then explores a file directory to discover module jars and extract their information.
	 */
	public default void explore(File directory) {
		List<File> files = getDiscoverer().explore(directory);
		List<I> infos = getInformationSupplier().supply(files);
		
		getModuleInfos().clear();
		infos.forEach(i -> getModuleInfos().put(i.getName(), i));
	}

	/**
	 * Instantiate AND enter a module with the entry point
	 */
	public default M instantiate(I info) {
		String msg = "BaseModuleManager instantiate error (name : " + info.getName() + ") : ";
		try {
			M mod = getModuleloader().instantiate(info);
			if(mod == null) {
				Log.error(msg + "instantiator result = null");
				return null;
			}
			// add the module to the list of instantiated modules
			getModules().put(info.getName(), mod);
			// enter the module in a thread
			//getExecutors().execute(() -> mod.enter(getEntry(), info));
			getExecutors().submit(() -> mod.enter(getEntry(), info));
			return mod;
		} catch (Exception | AbstractMethodError e) {
			Log.error(msg, e);
			return null;
		}
	}
	
	/**
	 * Instanciate AND enter every module with the entry point
	 */
	public default void instantiateAll() {
		getModuleInfos().values().forEach(this::instantiate);
	}
	
	/**
	 * Dispose a module, but keep its infos. This removes the module from the instantiated list and calls module.dispose();
	 */
	public default boolean dispose(I info) {
		M module = getModules().remove(info.getName());
		module.dispose();
		return true;
	}
	/**
	 * Dispose all modules, but keep their infos. This removes them from the instantiated list and calls module.dispose();
	 */
	public default void disposeAll() {
		getModuleInfos().values().forEach(this::dispose);
	}
	
	/**
	 * Get a Module implementation by its name
	 */
	public default M get(String name) {
		return getModules().get(name);
	}
	/**
	 * Get a Module implementation by its Information
	 */
	public default M get(I info) {
		return getModules().get(info.getName());
	}


	/**
	 * Get a Module Information
	 */
	public default I getInfo(String name) {
		return getModuleInfos().get(name);
	}
	/**
	 * Get all Module Informations
	 */
	public default Collection<I> getInfos() {
		return getModuleInfos().values();
	}
}
