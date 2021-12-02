package com.souchy.randd.modules.mydummyplugin;

import com.souchy.randd.modules.api.EntryPoint;
import com.souchy.randd.modules.api.ModuleClassLoader;
import com.souchy.randd.modules.api.ModuleInformation;
import com.souchy.randd.modules.base.BaseModule;
import com.souchy.randd.modules.monitor.modules.PluginMonitorEP;

public class MyDummyPlugin extends BaseModule {
	
	
	@Override
	public void enter(EntryPoint entry, ModuleInformation info) {
		// TODO Auto-generated method stub
		PluginMonitorEP e = (PluginMonitorEP) entry;
		
		String s = e.getSomething();
		System.out.println("enter " + s);
	}

	@Override
	public String doSomething() {
		return "fish";
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ModuleClassLoader getClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setClassLoader(ModuleClassLoader modclassloader) {
		// TODO Auto-generated method stub
		
	}


	
	
	
}
