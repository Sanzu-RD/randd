package data.modules;

import com.souchy.randd.modules.api.ModuleInformationSupplier;

public class AzurInformationSupplier implements ModuleInformationSupplier<AzurInformation> {

	@Override
	public Class<AzurInformation> getInformationClass() {
		return AzurInformation.class;
	}

}
