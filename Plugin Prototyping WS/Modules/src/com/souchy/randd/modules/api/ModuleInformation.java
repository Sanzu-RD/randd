package com.souchy.randd.modules.api;

import java.io.File;

public interface ModuleInformation {
	
	public File getJarFile();
	public void setJarFile(File f);
	
	public String getName();
	public String getDescription();
	public String getMainClass();


	/*
	# Nom du module
	"ModuleName": "Amethyst",
	# Date de release 
	"ModuleReleaseDate": "29-06-19",
	# Description 
	"ModuleDescription": "Hidden Piranh client",
	# Package+nom de la class module main 
	"ModuleMainClass":"com.souchy.randd.ebishoal.amethyst.main.Amethyst"
	
	#  Auteur
	"ModuleAuthor": "Robyn G.",
	# Version supportées de l'application parente
	"ModuleSupportedVersion": "0.0",
	# Dépendances à d'autres modules / libs 
	"ModuleDependencies":
	 */
	
	
}
