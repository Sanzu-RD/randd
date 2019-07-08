package com.souchy.randd.modules.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

import com.google.common.base.Charsets;
import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;
import com.souchy.randd.commons.tealwaters.logging.Log;


public interface ModuleInformationSupplier<I extends ModuleInformation> {

	public default I supply(File f) {
		I info = null;
		
		try(JarFile jar = new JarFile(f)) {
			ZipEntry entry = jar.getEntry("module.info");

			if(entry == null)  return info;

			InputStream in = jar.getInputStream(entry);
			String content = new String(in.readAllBytes(), Charsets.UTF_8);
			info = JsonConfig.gson.fromJson(content, getInformationClass());
			info.setJarFile(f);
			//info = getInformationClass().getConstructor().newInstance(); //new BaseModuleInformation(f, props);
			
			in.close();
		} catch (Exception e) {
			Log.warning("ModuleInformationSupplier error : ", e);
		}
		return info;
	}
	
	public default List<I> supply(List<File> files){
		return files.stream().map(this::supply).filter(x -> x != null).peek(i -> {
			try {
				Log.info("ModuleInfoSupplier found module info { " + i.getName() + " } in { "+i.getJarFile().getCanonicalPath()+" }");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).collect(Collectors.toList());
	}
	
	public Class<I> getInformationClass();
	
}
