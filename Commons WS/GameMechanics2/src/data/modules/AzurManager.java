package data.modules;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.souchy.randd.modules.api.EntryPoint;
import com.souchy.randd.modules.api.ModuleDiscoverer;
import com.souchy.randd.modules.api.ModuleInformationSupplier;
import com.souchy.randd.modules.api.ModuleInstantiator;
import com.souchy.randd.modules.api.ModuleManager;

public class AzurManager implements ModuleManager<AzurModule, AzurInformation> {

	private final EntryPoint entryPoint;
	private final AzurDiscoverer discoverer = new AzurDiscoverer();
	private final AzurInstantiator instantiator = new AzurInstantiator();
	private final AzurInformationSupplier informationSupplier = new AzurInformationSupplier();
	private final Map<String, AzurInformation> infos = new HashMap<>();
	private final Map<String, AzurModule> modules = new HashMap<>();
	private ExecutorService executors = Executors.newCachedThreadPool();
	
	public AzurManager() {
		this.entryPoint = new AzurEntryPoint();	
	}
	
	@Override
	public EntryPoint getEntry() {
		return entryPoint;
	}

	@Override
	public ModuleDiscoverer getDiscoverer() {
		return discoverer;
	}

	@Override
	public ModuleInstantiator<AzurModule, AzurInformation> getModuleloader() {
		return instantiator;
	}

	@Override
	public ModuleInformationSupplier<AzurInformation> getInformationSupplier() {
		return informationSupplier;
	}

	@Override
	public Map<String, AzurInformation> getModuleInfos() {
		return infos;
	}

	@Override
	public Map<String, AzurModule> getModules() {
		return modules;
	}
	
	public void disposeExecutors() {
//		try {
//			getExecutors().awaitTermination(10, TimeUnit.SECONDS);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} 
		getExecutors().shutdownNow();
		executors = Executors.newCachedThreadPool();
	}
	
	@Override
	public ExecutorService getExecutors() {
		return executors;
	}

}
