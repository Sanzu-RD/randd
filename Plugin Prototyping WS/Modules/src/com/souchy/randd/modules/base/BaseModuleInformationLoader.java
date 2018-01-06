package com.souchy.randd.modules.base;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

import com.souchy.randd.modules.api.ModuleInformationLoader;

public class BaseModuleInformationLoader implements ModuleInformationLoader {
	
		/**
		 * Ça devrait pas être ici, mais plutôt dans une classe telle <b>"ModuleExplorer" "ModuleSearcher"</b> ...<p>
		 * Ça devrait pas non plus être dans la même classe qu'un cache, (si on en a un). <p>
		 * Si on a un cache, la classe le contenant devrait avoir un nom approprié pour ça, peut utiliser une classe générale <b>"Cache"</b> qui serait dans un autre package/librairie. <p>
		 * Puis ensuite on peut créer <b>"ModuleManager"</b> qui utilise un <b>"Cache"</b>.
		 * @param directory
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public Map<String, BaseModuleInformation> loadModuleList(File directory) {
			Map<String, BaseModuleInformation> modulesList = Arrays.stream(directory.listFiles())
			.filter(file -> file.getName().endsWith(".jar"))
			.map(this::getModuleInformation)
			.filter(i -> i != null)
			.collect(Collectors.toMap(k -> k.getFile().getName(), v -> v));
			//.collect(Collectors.toMap(f -> f.getName(), f -> getModuleInformation(f)));
			return modulesList;
		}
		public void loadModule(File file){
			
		}
		private BaseModuleInformation getModuleInformation(File f) {
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
	}