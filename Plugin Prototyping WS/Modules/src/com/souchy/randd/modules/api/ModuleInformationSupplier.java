package com.souchy.randd.modules.api;

import java.io.File;
import java.util.List;


public interface ModuleInformationSupplier<T extends ModuleInformation> {

	public T supply(File f);
	
	public List<T> supply(List<File> files);
	
}
