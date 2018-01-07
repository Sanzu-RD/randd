package com.souchy.randd.modules.base;

import java.lang.reflect.Constructor;
import java.net.URL;

import com.souchy.randd.modules.base.BaseModuleInstanciator.BaseModClassLoader;

public class BaseModuleContainer {
	
	public String id;
	public BaseModuleInformation info;
	
	public BaseModClassLoader classloader;
	public BaseModule module;
	
	
	public BaseModuleContainer(BaseModuleInformation info) {
		this.id = info.getMainClass();
		this.info = info;
		
		/*try {
			this.loader = new BaseModClassLoader(info.getName(), new URL(info.getFile().getAbsolutePath()), ClassLoader.getPlatformClassLoader());
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}*/
	}
	//loader , get loaded classes // to see what's actually loaded onto the runtime so we know what we loaded and what we removed
	//th peux arriver la liste des classes loadee dans une treeview ou listviewdans l' ui de détails du root Sinai que
	public void load() {
		try {
			ClassLoader parent = ClassLoader.getPlatformClassLoader();
			URL fileUrl = info.getFile().toURI().toURL();
			classloader = new BaseModClassLoader(info.getName(), fileUrl, parent);
			
			@SuppressWarnings("unchecked")
			Class<BaseModule> modc = (Class<BaseModule>) Class.forName(info.getMainClass(), true, classloader);
			Constructor<BaseModule> c = modc.getConstructor(BaseModuleInformation.class);
			module = c.newInstance(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void unload() {
		try {
			module.doSomething();
			module = null;
			classloader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
