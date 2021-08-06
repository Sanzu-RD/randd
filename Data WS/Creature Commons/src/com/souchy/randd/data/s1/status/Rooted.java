package com.souchy.randd.data.s1.status;

import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.statusevents.displacement.DashEvent;
import com.souchy.randd.commons.diamond.statusevents.displacement.DashEvent.OnDashHandler;
import com.souchy.randd.commons.diamond.statusevents.displacement.DashToEvent;
import com.souchy.randd.commons.diamond.statusevents.displacement.DashToEvent.OnDashToHandler;
import com.souchy.randd.commons.diamond.statusevents.displacement.PushEvent;
import com.souchy.randd.commons.diamond.statusevents.displacement.PushEvent.OnPushHandler;
import com.souchy.randd.commons.diamond.statusevents.displacement.PushToEvent;
import com.souchy.randd.commons.diamond.statusevents.displacement.PushToEvent.OnPushToHandler;

public class Rooted extends Status implements OnDashHandler, OnDashToHandler, OnPushHandler, OnPushToHandler {

	public Rooted(Fight f, int sourceEntityId, int targetEntityId) {
		super(f, sourceEntityId, targetEntityId);
	}

	@Override
	public HandlerType type() {
		return HandlerType.Interceptor;
	}

	@Override
	public int modelid() {
		return 4;
	}

	@Override
	public Status create(Fight fight, int source, int target) {
		return new Rooted(fight, source, target);
	}

	@Override
	public Status copy0(Fight f) {
		return new Rooted(f, sourceEntityId, targetEntityId);
	}

	@Override
	public void onPushTo(PushToEvent e) {
		e.intercepted = true;
	}

	@Override
	public void onPush(PushEvent e) {
		e.intercepted = true;
	}

	@Override
	public void onDashTo(DashToEvent e) {
		e.intercepted = true;
	}

	@Override
	public void onDash(DashEvent e) {
		e.intercepted = true;
	}
	
}
