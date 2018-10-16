package com.souchy.randd.commons.tealwaters.commons;

import java.util.List;

public interface Discoverer<I, F, O> { // extends Predicate<O> {

	/**
	 * Explore a directory with the default filter : this.identify(F)
	 * @param directory
	 * @return
	 */
	//default List<O> explore(I directory) {
//		return explore(directory, this::identify);
	//}

	/**
	 * Only if you want to override the default filter of this discoverer. <p>
	 * The default is this.identify(F);
	 * @param directory
	 * @param filter
	 * @return
	 */
//	public List<O> explore(I directory, Predicate<F> filter);


	public List<O> explore(I directory);
	/**
	 * Default filter
	 * @param toFilter
	 * @return
	 */
	public boolean identify(F toFilter);

	/*
	 * @Override default boolean test(O out) { return identify(out); }
	 */

}
