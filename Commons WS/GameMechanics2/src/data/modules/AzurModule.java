package data.modules;

import java.util.ArrayList;

import com.souchy.randd.commons.tealwaters.commons.ClassInstanciator;
import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer.DefaultClassDiscoverer;
import com.souchy.randd.modules.api.EntryPoint;
import com.souchy.randd.modules.api.Module;
import com.souchy.randd.modules.api.ModuleClassLoader;
import com.souchy.randd.modules.api.ModuleInformation;

import data.new1.CreatureModel;
import data.new1.SpellDiscoverer;
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
	public AzurEntryPoint entry;
	
	public ModuleClassLoader getClassLoader() {
		return moduleClassloader;
	}
	public void setClassLoader(ModuleClassLoader moduleClassloader) {
		this.moduleClassloader = moduleClassloader;
	}

	
	@Override
	public void enter(EntryPoint entry, AzurInformation info) {
		this.info = info;
		this.entry = (AzurEntryPoint) entry;
		entry.getBus().register(this);
		discoverCreatures();
		discoverSpells();
		discoverItems();
		onInit(this.entry);
	}
	
	public String getDiscoveryPackage() {
		return this.getClass().getPackage().toString();
	}
	
	public void discoverCreatures() {
		var discoverer = new DefaultClassDiscoverer<CreatureModel>(CreatureModel.class);
		var creatureClasses = discoverer.explore(getDiscoveryPackage());
		var creatureModels = ClassInstanciator.<CreatureModel>list(creatureClasses);
		creatureModels.forEach(model -> entry.creatures.put(model.id(), model));
	}
	
	public void discoverSpells() {
		var discoverer = new DefaultClassDiscoverer<SpellModel>(SpellModel.class);
		var spellClasses = discoverer.explore(getDiscoveryPackage());
		var spells = ClassInstanciator.<SpellModel>list(spellClasses);
		spells.forEach(model -> entry.spells.put(model.id(), model));
	}
	
	public void discoverItems() {
		var discoverer = new DefaultClassDiscoverer<Item>(Item.class);
		var itemClasses = discoverer.explore(getDiscoveryPackage());
		var items = ClassInstanciator.<Item>list(itemClasses);
		items.forEach(model -> entry.items.put(model.id(), model));
	}
	
	/**
	 * Optional hook
	 */
	public void onInit(AzurEntryPoint entry) { }
	
	
}
