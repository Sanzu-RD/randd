package com.souchy.randd.modules.base;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.souchy.randd.modules.api.ModuleInformation;

public class BaseModuleInformation implements ModuleInformation {

	public static enum PropertiesNames {
		ModuleName, // Nom du module
		ModuleReleaseDate, // Date de release 
		ModuleDescription, // Description 
		ModuleAuthor, // Auteur
		ModuleSupportedVersions, // Version supportées de l'application parente par le module
		ModuleDependencies, // Dépendances à d'autres modules / libs 
		ModuleMainClass; // Package+nom de la class module main
	}
	
	private final File file;
	private final Properties props;
	
	public BaseModuleInformation(File f, Properties props) {
		file = f;
		this.props = props;
	}
	
	public File getFile(){
		return file;
	}
	public String getName(){
		return props.getProperty(PropertiesNames.ModuleName.name());
	}
	public String getReleaseDate(){
		return props.getProperty(PropertiesNames.ModuleReleaseDate.name());
	}
	public String getDescription(){
		return props.getProperty(PropertiesNames.ModuleDescription.name());
	}
	public String getAuthor(){
		return props.getProperty(PropertiesNames.ModuleAuthor.name());
	}
	public String getSupportedVersions(){
		return props.getProperty(PropertiesNames.ModuleSupportedVersions.name());
	}
	public String getDependencies(){
		return props.getProperty(PropertiesNames.ModuleDependencies.name());
	}
	public String getMainClass(){
		return props.getProperty(PropertiesNames.ModuleMainClass.name());
	}
	public Collection<String> getProps(){
		final List<String> list = new ArrayList<>();
		props.forEach((k, v) -> {
			list.add(k + " = " + v);
		});
		list.sort((s1, s2) -> s1.split("=")[0].length() - s2.split("=")[0].length());
		return list;
		//return props.values().stream().map(v -> (String) v).collect(Collectors.toList()); //.elements();
	}
	
	
	
	
}
