package com.souchy.randd.modules.node;

import com.souchy.randd.modules.api.ModuleInformationSupplier;

public class NodeInformationSupplier implements ModuleInformationSupplier<NodeInformation> {

	@Override
	public Class<NodeInformation> getInformationClass() {
		return NodeInformation.class;
	}

}
