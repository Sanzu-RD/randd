package com.souchy.randd.modules.base;

import com.randd.commons.tealwaters.commons.Disposable;
import com.souchy.randd.modules.api.Module;

public abstract class BaseModule implements Module, Disposable {

	public abstract String doSomething();

}
