package com.souchy.randd.modules.node;

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

public class NodeManager implements ModuleManager<NodeModule, NodeInformation> {

	private final EntryPoint entryPoint;
	private final NodeDiscoverer discoverer = new NodeDiscoverer();
	private final NodeInstantiator instantiator = new NodeInstantiator();
	private final NodeInformationSupplier informationSupplier = new NodeInformationSupplier();
	private final Map<String, NodeInformation> infos = new HashMap<>();
	private final Map<String, NodeModule> modules = new HashMap<>();
	private ExecutorService executors = Executors.newCachedThreadPool();
	
	public NodeManager(EntryPoint entryPoint) {
		this.entryPoint = entryPoint;	
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
	public ModuleInstantiator<NodeModule, NodeInformation> getModuleloader() {
		return instantiator;
	}

	@Override
	public ModuleInformationSupplier<NodeInformation> getInformationSupplier() {
		return informationSupplier;
	}

	@Override
	public Map<String, NodeInformation> getModuleInfos() {
		return infos;
	}

	@Override
	public Map<String, NodeModule> getModules() {
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
