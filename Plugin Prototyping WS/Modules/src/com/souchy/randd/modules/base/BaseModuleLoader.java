package com.souchy.randd.modules.base;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import com.souchy.randd.modules.api.ModuleInformation;
import com.souchy.randd.modules.api.ModuleLoader;
import com.souchy.randd.modules.api.ModuleManager;

public class BaseModuleLoader implements ModuleLoader {


	@Override
	public BaseModule load(ModuleInformation inf) {
		// TODO Auto-generated method stub
		
		BaseModule module = null;
		BaseModuleInformation info = (BaseModuleInformation) inf;
		
		try {
			ClassLoader parent = ClassLoader.getPlatformClassLoader();
			URL fileUrl = info.getFile().toURI().toURL();
			BaseModClassLoader modclassloader = new BaseModClassLoader(info.getName(), fileUrl, parent);
			
			@SuppressWarnings("unchecked")
			Class<BaseModule> modc = (Class<BaseModule>) Class.forName(info.getMainClass(), true, modclassloader);
			Constructor<BaseModule> c = modc.getConstructor(BaseModuleInformation.class);
			module = c.newInstance(info);
		}catch(Exception e) {
			e.printStackTrace();
		}
		/*try(JarFile jar = new JarFile(info.getFile())) {
			ZipEntry entry = jar.getEntry(info.getMainClass());
			
			if(entry == null) return module;
			
			InputStream in = jar.getInputStream(entry);

			ClassLoader.getPlatformClassLoader();
			ClassLoader.getSystemClassLoader();
			
			URLClassLoader.newInstance(null, null); //.newInstance(null); // new URL(info.getFile().getAbsolutePath())
			
			BaseModClassLoader modloader = new BaseModClassLoader(info.getName(), new URL(info.getFile().getAbsolutePath()), ClassLoader.getPlatformClassLoader());
			Class<BaseModule> modc = (Class<BaseModule>) Class.forName(info.getMainClass(), true, modloader);
			Constructor<BaseModule> c = modc.getConstructor(BaseModuleInformation.class);
			module = c.newInstance(info);
			
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return module;
	}

	@Override
	public void load(ModuleInformation info, ModuleManager destination) {
		// TODO Auto-generated method stub
		
	}

	public static class BaseModuleContainer {
		
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
	
	public static class BaseModClassLoader extends URLClassLoader {
		
		public BaseModClassLoader(String arg0, URL arg1, ClassLoader arg2) {
			super(arg0, new URL[0], arg2);
			// TODO Auto-generated constructor stub
		}
		
	}
	
	
	
}
