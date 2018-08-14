package com.souchy.randd.commons.tealwaters.commons;

public interface Serializer<I, O> { // extends Responsibility<I, O> {

	public O serialize(I out);

}
