package com.souchy.randd.modules.api;

import java.io.File;
import java.util.Map;

public interface ModuleInformationLoader {
	public <T extends ModuleInformation> Map<String, T> loadModuleList(File directory);
}
