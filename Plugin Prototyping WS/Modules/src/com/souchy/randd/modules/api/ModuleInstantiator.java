package com.souchy.randd.modules.api;

import java.lang.reflect.Constructor;
import java.net.URL;

import com.souchy.randd.commons.tealwaters.logging.Log;

/**
 * Called ModuleLoader before
 * 
 * @author Souchy
 *
 */
public interface ModuleInstantiator<M extends Module<I>, I extends ModuleInformation> {
	
	/**
	 * Called load(..) before
	 * 
	 * @param info
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public default M instantiate(I info) {
		M module = null;
		try {
			ClassLoader parent = this.getClass().getClassLoader(); //ClassLoader.getPlatformClassLoader(); //ClassLoader.getSystemClassLoader(); //
			//Log.info("Parent class loader : " + parent + ", " + parent.getName() + ", " + parent.getDefinedPackages());
			URL fileUrl = info.getJarFile().toURI().toURL();
			ModuleClassLoader modclassloader = new ModuleClassLoader(info.getName(), fileUrl, parent);
			
			var clazz = modclassloader.loadClass(info.getMainClass());
			//Log.info("Loaded module class : " + clazz.descriptorString() + ", is it [" + (Module.class.isAssignableFrom(clazz)) + ", " + (NodeModule.class.isAssignableFrom(clazz)) + "]");
			Class<M> moduleClass = (Class<M>) clazz;
			Constructor<M> c = moduleClass.getConstructor();
			module = c.newInstance();
			module.setClassLoader(modclassloader);
			//modclassloader.close();
		} catch (Exception e) {
			Log.error("ModuleInstantiator error : ", e);
			System.exit(0);
		}
		return module;
	}
	
}
