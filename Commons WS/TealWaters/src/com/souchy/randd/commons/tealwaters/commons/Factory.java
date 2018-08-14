package com.souchy.randd.commons.tealwaters.commons;

/**
 * Self factory
 * 
 * @author Souchy
 *
 * @param <O>
 */
@FunctionalInterface
public interface Factory<O> { // extends Supplier<O> { //, Identifiable<Integer> {

	/*
	 * default O get() { return create(); }
	 */
	public O create();

}
