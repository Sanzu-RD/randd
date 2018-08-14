package tests.functional;

import java.io.File;
import java.util.Properties;

import com.souchy.randd.commons.tealwaters.properties.PropertyConfig;

public class DummyClass implements PropertyConfig {

	@Override
	public File getFile() {
		return null;
	}

	@Override
	public Properties getProperties() {
		return null;
	}


}
