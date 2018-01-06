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
		 * MOVE TO ANOTHER CLASS<p>
		 * Ça devrait pas être ici, mais plutôt dans une classe telle <b>"ModuleExplorer" "ModuleSearcher"</b> ...<p>
		 * Ça devrait pas non plus être dans la même classe qu'un cache, (si on en a un). <p>
		 * Si on a un cache, la classe le contenant devrait avoir un nom approprié pour ça, peut utiliser une classe générale <b>"Cache"</b> qui serait dans un autre package/librairie. <p>
		 * Puis ensuite on peut créer <b>"ModuleManager"</b> qui utilise un <b>"Cache"</b>.
		 * @param directory
		 * @return
		 * @Deprecated "move to another class"
		 */
		 @Deprecated
		public Map<String, BaseModuleInformation> loadModuleList(File directory) {
			Map<String, BaseModuleInformation> modulesList = Arrays.stream(directory.listFiles())
			.filter(file -> file.getName().endsWith(".jar"))
			.collect(Collectors.toMap(k -> k.getName(), this::getModuleInformation));
			//.map(this::getModuleInformation)
			//.collect(Collectors.toMap(k -> k.getFile().getName(), v -> v));
			return modulesList;
		}
		public void loadModule(File file){
			
		}
		private BaseModuleInformation getModuleInformation(File f){
			BaseModuleInformation info = null;
			try {
				JarFile jar = new JarFile(f);
				ZipEntry entry = jar.getEntry("info.properties");
				InputStream in = jar.getInputStream(entry);
				Properties props = new Properties();
				props.load(in);
				info = new BaseModuleInformation(f, props);
				in.close();
				jar.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			return info;
		}
	}