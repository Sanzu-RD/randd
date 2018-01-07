package com.souchy.randd.modules.api;

import java.io.File;
import java.util.Collection;

public interface ModuleManager<M extends Module, I extends ModuleInformation> {

	public void explore(File directory);
	
	public M instanciate(I info) throws Exception;
	public void instanciateAll() throws Exception;
	public boolean dispose(I info);
	
	public M get(String name);
	public M get(I info);

	public I getInfo(String name);
	public Collection<I> getInfos();
}
