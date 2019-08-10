package data.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.tealwaters.commons.ClassInstanciator;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.modules.api.ModuleClassLoader;
import com.souchy.randd.modules.api.ModuleInstantiator;

import data.new1.CreatureModel;
import data.new1.SpellModel;
import gamemechanics.models.Item;

public class AzurInstantiator implements ModuleInstantiator<AzurModule, AzurInformation> {

	@SuppressWarnings("unchecked")
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
			//modclassloader.close();
		} catch (Exception e) {
			Log.error("ModuleInstantiator error : ", e);
			System.exit(0);
		}
		return module;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AzurModule instantiate(AzurInformation info, AzurCache azurCache) {
		try {
			var module = instantiate(info);

			var command = "jar tf " + info.getJarFile().getAbsolutePath();
			//Log.info("AzurInstantiator command : " + command);
			Process proc = Runtime.getRuntime().exec(command);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null;
			List<Class> classes = new ArrayList<>();
			while((line = stdInput.readLine()) != null) {
				//Log.info("AzurInstantiator line : " + line);
				if(line.endsWith(".class")) {
					line = line.replace('/', '.');
					line = line.substring(0, line.indexOf(".class"));
					if(line.contentEquals("module-info") == false)
						classes.add(module.getClassLoader().loadClass(line));
				}
			}
		
			classes.forEach(c -> {
				if(CreatureModel.class.isAssignableFrom(c)) {
					var model = ClassInstanciator.<CreatureModel>create(c);
					azurCache.creatures.put(model.id(), model);
				}
				if(SpellModel.class.isAssignableFrom(c)) {
					var model = ClassInstanciator.<SpellModel>create(c);
					azurCache.spells.put(model.id(), model);
				}
				if(Item.class.isAssignableFrom(c)) {
					var model = ClassInstanciator.<Item>create(c);
					azurCache.items.put(model.id(), model);
				}
			});
//			Log.info("AzurInstantiator : creatureModels : " + azurCache.creatures.values());
//			Log.info("AzurInstantiator : spells : " + azurCache.spells.values());
//			Log.info("AzurInstantiator : items : " + azurCache.items.values());
			
			//modclassloader.close();
			return module;
		} catch (Exception e) {
			Log.error("ModuleInstantiator error : ", e);
			System.exit(0);
			return null;
		}
	}
}
