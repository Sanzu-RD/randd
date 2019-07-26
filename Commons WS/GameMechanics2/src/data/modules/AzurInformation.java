package data.modules;

import java.io.File;

import com.souchy.randd.commons.tealwaters.io.files.JsonHelpers.Exclude;
import com.souchy.randd.modules.api.ModuleInformation;

public class AzurInformation implements ModuleInformation {
	
	@Exclude
	private File file;
	
	public String ModuleName; 		// Nom du module
	public String ModuleReleaseDate;// Date de release 
	public String ModuleDescription;// Description 
	public String ModuleMainClass; 	// Package+nom de la class module main	
	public String ModuleSeason; 	// what season this azur module was made for
	
	@Override
	public File getJarFile() {
		return file;
	}
	@Override
	public void setJarFile(File f) {
		file = f;
	}

	@Override
	public String getName() {
		return ModuleName;
	}

	@Override
	public String getDescription() {
		return ModuleDescription;
	}
	
	@Override
	public String getMainClass() {
		return ModuleMainClass;
	}

}
