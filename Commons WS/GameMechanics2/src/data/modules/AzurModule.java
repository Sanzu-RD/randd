package data.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.souchy.randd.commons.tealwaters.commons.ClassInstanciator;
import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer.DefaultClassDiscoverer;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.modules.api.EntryPoint;
import com.souchy.randd.modules.api.Module;
import com.souchy.randd.modules.api.ModuleClassLoader;
import com.souchy.randd.modules.api.ModuleInformation;

import data.new1.CreatureModel;
import data.new1.SpellModel;
import gamemechanics.models.Item;

public abstract class AzurModule implements Module<AzurInformation> {
	
	/**
	 * The class loader for this module. 
	 * It will be set on ModuleManager.instanciate. 
	 * It will be closed on dispose();
	 */
	private ModuleClassLoader moduleClassloader;
	
	public AzurInformation info;

	public ModuleClassLoader getClassLoader() {
		return moduleClassloader;
	}
	public void setClassLoader(ModuleClassLoader moduleClassloader) {
		this.moduleClassloader = moduleClassloader;
	}
	
	
	@Override
	public void enter(EntryPoint entry, AzurInformation info) {
		this.info = info;
		entry.getBus().register(this);
		onInit((AzurCache) entry);
	}
	
	public String getDiscoveryPackage() {
		return this.getClass().getPackage().getName();
	}
	
	/**
	 * Optional hook
	 */
	public void onInit(AzurCache entry) { }
	
	
}
