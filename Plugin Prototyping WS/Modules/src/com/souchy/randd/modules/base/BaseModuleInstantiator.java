package com.souchy.randd.modules.base;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

import com.souchy.randd.commons.tealwaters.logging.Log;

import com.souchy.randd.modules.api.Module;
import com.souchy.randd.modules.api.ModuleInstantiator;
import com.souchy.randd.modules.api.ModuleInstantiator.ModClassLoader;

public class BaseModuleInstantiator<M extends Module> implements ModuleInstantiator<M, BaseModuleInformation> {
	
	@Override
	public M instantiate(BaseModuleInformation info) {
		M module = null;
		try {
			ClassLoader parent = ClassLoader.getPlatformClassLoader();
			URL fileUrl = info.getJarFile().toURI().toURL();
			ModClassLoader modclassloader = new ModClassLoader(info.getName(), fileUrl, parent);
			
			@SuppressWarnings("unchecked")
			Class<M> modc = (Class<M>) modclassloader.loadClass(info.getMainClass()); 
			Constructor<M> c = modc.getConstructor();
			module = c.newInstance();
			modclassloader.close();
		} catch (Exception e) {
			Log.error("BaseModuleInstantiator instantiate error : ", e);
			System.exit(0);
		}
		return module;
	}
	
	
	/*
	 * public static class ModuleReference extends SoftReference<BaseModule> {
	 * BaseModuleManager m; private String moduleName; public
	 * ModuleReference(BaseModuleManager m, String moduleName) { super(null); }
	 * 
	 * @Override public BaseModule get() { return m.get(moduleName); } }
	 */
	
}
