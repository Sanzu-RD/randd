package com.souchy.randd.commons.tealwaters.commons;

@FunctionalInterface
public interface Deserializer<I, O> { // extends Responsibility<I, O> {

	public O deserialize(I in);

}
