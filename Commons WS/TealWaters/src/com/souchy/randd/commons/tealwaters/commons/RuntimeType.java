package com.souchy.randd.commons.tealwaters.commons;

import static com.souchy.randd.commons.tealwaters.commons.RuntimeType.RuntimeTypes.Development;

public class RuntimeType {
	
	
	public enum RuntimeTypes {
		Development, 
		Production,
	}
	
	
	private static RuntimeTypes runtime; 
	
	
	public boolean isDev() {
		return runtime == Development;
	}
	
	
	
}
