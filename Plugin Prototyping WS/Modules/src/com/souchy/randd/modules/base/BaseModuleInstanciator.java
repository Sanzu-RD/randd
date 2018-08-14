package com.souchy.randd.modules.base;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

import com.souchy.randd.modules.api.ModuleInstanciator;


public class BaseModuleInstanciator implements ModuleInstanciator<BaseModule, BaseModuleInformation> {

	@Override
	public BaseModule instanciate(BaseModuleInformation info) {
		BaseModule module = null;
		
		try {
			ClassLoader parent = ClassLoader.getPlatformClassLoader();
			URL fileUrl = info.getFile().toURI().toURL();
			BaseModClassLoader modclassloader = new BaseModClassLoader(info.getName(), fileUrl, parent);
			
			//System.out.println("ModuleLoad path : " + modclassloader.getURLs()[0].toString());
			
			@SuppressWarnings("unchecked")
			Class<BaseModule> modc = (Class<BaseModule>) modclassloader.loadClass(info.getMainClass()); //Class.forName("src/"+info.getMainClass(), true, modclassloader);
			Constructor<BaseModule> c = modc.getConstructor();
			module = c.newInstance();
			modclassloader.close();
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		//ClassLoader.getPlatformClassLoader();
		//ClassLoader.getSystemClassLoader();
		return module;
	}
	
	public static class BaseModClassLoader extends URLClassLoader {
		public BaseModClassLoader(String name, URL url, ClassLoader loader) {
			super(name, new URL[]{url}, loader);
		}
	}
	
	/*public static class ModuleReference extends SoftReference<BaseModule> {
		BaseModuleManager m;
		private String moduleName;
		public ModuleReference(BaseModuleManager m, String moduleName) {
			super(null);
		}
		@Override
		public BaseModule get() {
			return m.get(moduleName);
		}
	}*/

	
}
