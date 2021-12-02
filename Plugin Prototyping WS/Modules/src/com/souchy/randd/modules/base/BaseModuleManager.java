package com.souchy.randd.modules.base;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.modules.api.EntryPoint;
import com.souchy.randd.modules.api.Module;
import com.souchy.randd.modules.api.ModuleManager;
import com.souchy.randd.modules.api.ModuleDiscoverer;
import com.souchy.randd.modules.api.ModuleInformation;
import com.souchy.randd.modules.api.ModuleInformationSupplier;
import com.souchy.randd.modules.api.ModuleInstantiator;

public class BaseModuleManager<M extends Module<BaseModuleInformation>, I extends BaseModuleInformation> implements ModuleManager<M, I> {
	
	
	/**
	 * Point d'accès donné aux modules
	 */
	private EntryPoint entry;
	/**
	 * Information loader, finds jars and reads their info.properties file.
	 */
	private BaseModuleDiscoverer discoverer;
	/**
	 * Module loader, creates a modclassloader and loads the modules's mainclass into it
	 */
	private BaseModuleInstantiator<M> moduleloader;
	/**
	 * Lists jars informations, indexed by file name
	 */
	private Map<String, I> moduleInfos = new HashMap<>();
	/**
	 * Instances de modules chargés
	 */
	private Map<String, M> modules = new HashMap<>();
	
	
	public BaseModuleManager(BaseModuleDiscoverer infoloader, BaseModuleInstantiator<M> moduleloader, EntryPoint entry){
		this.discoverer = infoloader;
		this.moduleloader = moduleloader;
		this.entry = entry;
	}
	
	public BaseModuleManager(EntryPoint entry){
		this(new BaseModuleDiscoverer(), new BaseModuleInstantiator<M>(), entry);
	}
	
	public void explore(File directory){
		List<File> files = discoverer.explore(directory);
		List<I> infos = new BaseModuleInformationSupplier().supply(files);
		
		moduleInfos.clear();
		infos.forEach(i -> moduleInfos.put(i.getName(), i));
	}
	
	/**
	 * Instanciate AND enter a module with the entry point
	 */
	public M instantiate(I info) {
		var msg = "BaseModuleManager instantiate error (name : " + info.getName() + ") : ";
		try {
			M mod = moduleloader.instantiate(info);
			if(mod == null) {
				Log.error(msg + "instantiator result = null");
				return null;
			}
			modules.put(info.getName(), mod);
			mod.enter(entry, info);
			return mod;
		} catch (Exception | AbstractMethodError e) {
			Log.error(msg, e);
			return null;
		}
	}
	
	/**
	 * Instanciate AND enter every module with the entry point
	 */
	public void instantiateAll() {
		moduleInfos.values().forEach(this::instantiate);
	}

	public boolean dispose(I info) {
		M module = modules.remove(info.getName());
		if(module == null) return false;
		module.dispose();
		return true;
	}
	
	public M get(String name) {
		return modules.get(name);
	}
	public M get(I info) {
		if(info == null) return null;
		return modules.get(info.getName());
	}
	public I getInfo(String name) {
		return moduleInfos.get(name);
	}
	public Collection<I> getInfos() {
		return moduleInfos.values();
	}

	@Override
	public EntryPoint getEntry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseModuleDiscoverer getDiscoverer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModuleInstantiator<M, I> getModuleloader() {
		return moduleloader;
	}

	@Override
	public ModuleInformationSupplier<I> getInformationSupplier() {
		return (ModuleInformationSupplier<I>) new BaseModuleInformationSupplier();
	}

	@Override
	public Map<String, I> getModuleInfos() {
		return moduleInfos;
	}

	@Override
	public Map<String, M> getModules() {
		return modules;
	}

	@Override
	public ExecutorService getExecutors() {
		// TODO Auto-generated method stub
		return null;
	}

}
