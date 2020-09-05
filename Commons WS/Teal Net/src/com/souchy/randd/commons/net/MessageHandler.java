package com.souchy.randd.commons.net;

import com.souchy.randd.commons.tealwaters.commons.AnnotatedIdentifiable;
import com.souchy.randd.commons.tealwaters.commons.Identifiable;

public interface MessageHandler<C, M extends Message<?, ?>> extends Identifiable<Integer>
// ,Responsibility<M, ?>
{

	@Override
	default Integer getID() {
		return AnnotatedIdentifiable.getID(getMessageClass());
	}

	/**
	 * Handle message, then post it on the core's event bus
	 * @param client
	 * @param message
	 */
	public void handle(C client, M message);

	public Class<M> getMessageClass();

}
