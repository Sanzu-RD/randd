package com.randd.commons.tealwaters.commons;

import java.util.List;

public interface Discoverer<I, F, O> { //extends Predicate<O> {


	public List<O> explore(I directory);
	
	public boolean identify(F filter);
	
	/*@Override
	default boolean test(O out) {
		return identify(out);
	}*/
	
}
