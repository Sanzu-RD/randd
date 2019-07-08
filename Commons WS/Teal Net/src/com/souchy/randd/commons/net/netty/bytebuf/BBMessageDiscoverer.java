package com.souchy.randd.commons.net.netty.bytebuf;


import com.souchy.randd.commons.tealwaters.io.files.ClassDiscoverer;

public class BBMessageDiscoverer extends ClassDiscoverer<BBMessage> {

	@Override
	public boolean identify(Class<?> c) {
		return c != BBMessage.class && BBMessage.class.isAssignableFrom(c);
	}
	
	
	/*
	public static class BBMessageDiscoverer extends ClassDiscoverer {
		@Override
		public List<Class<BBMessage>> explore(String directory, Predicate<Class<?>> filter) {
			// tODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean identify(Class<?> c) {
			return BBMessage.class.isAssignableFrom(c);
		}
	}
	 */
	
	

}
