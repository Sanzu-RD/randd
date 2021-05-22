package com.souchy.randd.data.s1.status;

import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Status;

public class Rooted extends Status {

	public Rooted(Fight f, int sourceEntityId, int targetEntityId) {
		super(f, sourceEntityId, targetEntityId);
	}

	@Override
	public HandlerType type() {
		return HandlerType.Interceptor;
	}

	@Override
	public int modelid() {
		return 0;
	}

	@Override
	public Status create(Fight fight, int source, int target) {
		return new Rooted(fight, source, target);
	}

	@Override
	public Status copy0(Fight f) {
		return new Rooted(f, sourceEntityId, targetEntityId);
	}
	
}
