package com.souchy.randd.commons.net;

import com.souchy.randd.commons.tealwaters.commons.Identifiable;

public interface Session<T extends Message<?, ?>> extends Identifiable<Integer> {

	public void write(T msg);

}
