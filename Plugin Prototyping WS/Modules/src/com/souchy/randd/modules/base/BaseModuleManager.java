package com.souchy.randd.modules.base;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.souchy.randd.modules.api.EntryPoint;
import com.souchy.randd.modules.api.ModuleManager;

public class BaseModuleManager implements ModuleManager<BaseModule, BaseModuleInformation> {


	
	/**
	 * Information loader, finds jars and reads their info.properties file.
	 */
	private BaseModuleDiscoverer discoverer;
	/**
	 * Lists jars informations, indexed by file name
	 */
	private Map<String, BaseModuleInformation> moduleInfos = new HashMap<>();
	
	/**
	 * Module loader, creates a modclassloader and loads the modules's mainclass into it
	 */
	private BaseModuleInstanciator moduleloader;
	/**
	 * Instances de modules chargés
	 */
	private Map<String, BaseModule> modules = new HashMap<>();
	/**
	 * Point d'accès donné aux modules
	 */
	private EntryPoint entry;
	
	
	public BaseModuleManager(BaseModuleDiscoverer infoloader, BaseModuleInstanciator moduleloader, EntryPoint entry){
		this.discoverer = infoloader;
		this.moduleloader = moduleloader;
		this.entry = entry;
	}
	
	public BaseModuleManager(EntryPoint entry){
		this(new BaseModuleDiscoverer(), new BaseModuleInstanciator(), entry);
	}
	
	public void explore(File directory){
		List<File> files = discoverer.explore(directory);
		List<BaseModuleInformation> infos = new BaseModuleInformationSupplier().supply(files);
		
		moduleInfos.clear();
		infos.forEach(i -> moduleInfos.put(i.getName(), i));
		//moduleInfos.putAll();
	}
	public BaseModule instanciate(BaseModuleInformation info) throws Exception {
		try {
			BaseModule mod = moduleloader.instanciate(info);
			if(mod == null) return null;
			modules.put(info.getName(), mod);
			mod.enter(entry, info);
			return mod;
		} catch(Exception | AbstractMethodError e) {
			throw new Exception(e);
		}
	}
	public boolean dispose(BaseModuleInformation info) {
		BaseModule module = modules.remove(info.getName());
		if(module == null) return false;
		module.dispose();
		return true;
	}

	public void instanciateAll() throws Exception {
		Consumer<BaseModuleInformation> safeInstanciate = (BaseModuleInformation info) -> {
			try {
				instanciate(info);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
		try {
			moduleInfos.values().forEach(safeInstanciate);
		} catch(Exception e) {
			throw new Exception(e);
		}
	}

	public BaseModule get(String name) {
		return modules.get(name);
	}
	public BaseModule get(BaseModuleInformation info) {
		if(info == null) return null;
		return modules.get(info.getName());
	}
	/*public BaseModuleInformation getInfo(int i) {
		Object[] infos = moduleInfos.values().toArray();
		if(i < 0 || i >= infos.length) return null;
		return (BaseModuleInformation) infos[i];
	}*/
	public BaseModuleInformation getInfo(String name) {
		return moduleInfos.get(name);
	}
	public Collection<BaseModuleInformation> getInfos() {
		return moduleInfos.values();
	}

}
