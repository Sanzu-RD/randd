package com.souchy.randd.modules.api;

import java.net.URL;
import java.net.URLClassLoader;

public class ModuleClassLoader extends URLClassLoader {
	
	public ModuleClassLoader(String name, URL url, ClassLoader loader) {
		super(name, new URL[] { url }, loader);
		
		
	}
	
}
