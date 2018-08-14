package com.souchy.randd.modules.node;

import java.io.File;
import java.util.Collection;

import com.souchy.randd.modules.api.ModuleManager;

public class NodeManager implements ModuleManager<NodeModule, NodeInformation> {

	@Override
	public void explore(File directory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NodeModule instanciate(NodeInformation info) {
		// tODO Auto-generated method stub
		return null;
	}

	@Override
	public void instanciateAll() {
		// tODO Auto-generated method stub
		
	}

	@Override
	public boolean dispose(NodeInformation info) {
		// tODO Auto-generated method stub
		return false;
	}

	@Override
	public NodeModule get(String name) {
		// tODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeModule get(NodeInformation info) {
		// tODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeInformation getInfo(String name) {
		// tODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<NodeInformation> getInfos() {
		// tODO Auto-generated method stub
		return null;
	}

}
