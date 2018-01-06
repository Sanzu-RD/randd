package com.souchy.randd.modules.base;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.souchy.randd.modules.api.ModuleInformationLoader;
import com.souchy.randd.modules.api.ModuleLoader;
import com.souchy.randd.modules.api.ModuleManager;

public class BaseModuleManager implements ModuleManager {
	
	//public List<File> moduleJarList;
	
	/**
	 * Information loader, finds jars and reads their info.properties file.
	 */
	private ModuleInformationLoader infoloader;
	/**
	 * Lists jars informations, indexed by file name
	 */
	private Map<String, BaseModuleInformation> moduleJarList = new HashMap<>();
	
	/**
	 * 
	 */
	private ModuleLoader moduleloader;
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private Map<String, BaseModule> modules = new HashMap<>();
	
	
	public BaseModuleManager(ModuleInformationLoader infoloader, ModuleLoader moduleloader){
		this.infoloader = infoloader;
		this.moduleloader = moduleloader;
	}
	
	public Map<String, BaseModuleInformation> getModulesInfoList(){
		return moduleJarList;
	}
	
	public void addDirectory(File directory){
		moduleJarList.putAll(infoloader.loadModuleList(directory));
	}
	public void removeDirectory(File directory){
		Iterator<Entry<String, BaseModuleInformation>> it = moduleJarList.entrySet().iterator();
		it.forEachRemaining(c -> {
			if(c.getValue().getFile().getParentFile() == directory)
				it.remove();
		});
	}
	
	public void load(BaseModuleInformation info){
		moduleloader.load(info);
	}
	
	
	

}
