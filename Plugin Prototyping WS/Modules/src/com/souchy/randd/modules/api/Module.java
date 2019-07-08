package com.souchy.randd.modules.api;

import java.io.IOException;

import com.souchy.randd.commons.tealwaters.commons.Disposable;
import com.souchy.randd.commons.tealwaters.logging.Log;

public interface Module<I extends ModuleInformation> extends Disposable {
	
	public void enter(EntryPoint entry, I info);

	public ModuleClassLoader getClassLoader();
	public void setClassLoader(ModuleClassLoader modclassloader);

	/**
	 * Need to close the classloader when unloading the classes from this module
	 */
	public default void dispose() {
		try {
			getClassLoader().close();
			setClassLoader(null);
		} catch (IOException e) {
			Log.error("Disposing module error : ", e);
		}
	}
	
}
