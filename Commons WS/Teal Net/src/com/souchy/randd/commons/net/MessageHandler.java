package com.souchy.randd.commons.net;

import com.souchy.randd.commons.tealwaters.commons.AnnotatedIdentifiable;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;

public interface MessageHandler<M extends Message<?, ?>> extends Identifiable<Integer>
// ,Responsibility<M, ?>
{

	@Override
	default Integer getID() {
		return AnnotatedIdentifiable.getID(getMessageClass());
	}

	public void handle(M message);

	public Class<M> getMessageClass();

}
