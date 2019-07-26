package data.modules;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.modules.api.ModuleDiscoverer;

public class AzurDiscoverer implements ModuleDiscoverer {

	@Override
	public boolean identify(File file) {
		if (!file.getName().endsWith(".jar"))
			return false;
		try (JarFile jar = new JarFile(file)) {
			ZipEntry entry = jar.getEntry("module.info");
			return (entry != null);
		} catch (IOException e) {
			Log.error("NodeDiscoverer identify error : ", e);
			return false;
		}
	}

}
