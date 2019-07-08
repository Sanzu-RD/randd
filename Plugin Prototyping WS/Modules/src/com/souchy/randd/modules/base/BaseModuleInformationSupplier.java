package com.souchy.randd.modules.base;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.modules.api.ModuleInformation;
import com.souchy.randd.modules.api.ModuleInformationSupplier;

public class BaseModuleInformationSupplier implements ModuleInformationSupplier<BaseModuleInformation> {

	public Class<BaseModuleInformation> getInformationClass(){
		return BaseModuleInformation.class;
	}
	/*
	public BaseModuleInformation supply(File f) {
		BaseModuleInformation info = null;
		
		try(JarFile jar = new JarFile(f)) {
			ZipEntry entry = jar.getEntry("info.properties");

			if(entry == null)  return info;

			InputStream in = jar.getInputStream(entry);
			Properties props = new Properties();
			props.load(in);
			info = new BaseModuleInformation(f, props);

			in.close();
			// jar.close(); // pu besoin, grâce au try-with-resource ça close automatiquement
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return info;
	}
	*/

	@Override
	public List<BaseModuleInformation> supply(List<File> files) {
		return files.stream().map(this::supply).filter(x -> x != null).peek(i -> Log.info("BaseModuleInfoSupplier found module info : " + i.getName())).collect(Collectors.toList());
	}
	
	
}
