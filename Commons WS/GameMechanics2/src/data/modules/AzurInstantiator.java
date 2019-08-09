package data.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.URL;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.modules.api.ModuleClassLoader;
import com.souchy.randd.modules.api.ModuleInstantiator;

public class AzurInstantiator implements ModuleInstantiator<AzurModule, AzurInformation> {
	@Override
	public AzurModule instantiate(AzurInformation info) {
		AzurModule module = null;
		try {
			ClassLoader parent = this.getClass().getClassLoader(); //ClassLoader.getPlatformClassLoader(); //ClassLoader.getSystemClassLoader(); //
			//Log.info("Parent class loader : " + parent + ", " + parent.getName() + ", " + parent.getDefinedPackages());
			URL fileUrl = info.getJarFile().toURI().toURL();
			ModuleClassLoader modclassloader = new ModuleClassLoader(info.getName(), fileUrl, parent);

			// example : "ModuleMainClass": "com.souchy.randd.data.creatures.sungjin.SungjinModule"
			var clazz = modclassloader.loadClass(info.getMainClass());
			Class<AzurModule> moduleClass = (Class<AzurModule>) clazz;
			Constructor<AzurModule> constructor = moduleClass.getConstructor();
			module = constructor.newInstance();
			module.setClassLoader(modclassloader);
			
			Process proc = Runtime.getRuntime().exec("jar tvf " + info.getJarFile().getAbsolutePath());
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null;
			while((line = stdInput.readLine()) != null) {
				if(line.endsWith(".class")) {
					line = line.replace('/', '.');
					modclassloader.loadClass(line);
				}
			}
			
			//modclassloader.close();
		} catch (Exception e) {
			Log.error("ModuleInstantiator error : ", e);
			System.exit(0);
		}
		return module;
	}
}
