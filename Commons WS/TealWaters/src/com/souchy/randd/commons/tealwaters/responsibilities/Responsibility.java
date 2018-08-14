package com.souchy.randd.commons.tealwaters.responsibilities;

import com.souchy.randd.commons.tealwaters.commons.Identifiable;

public interface Responsibility<I, O> extends Identifiable<Integer> {

	public O handle(I in);

}
