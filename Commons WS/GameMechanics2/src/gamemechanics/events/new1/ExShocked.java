package gamemechanics.events.new1;

import data.new1.timed.Status;
import gamemechanics.events.new1.damage.OnDmgEvent;
import gamemechanics.events.new1.damage.OnDmgEvent.OnDmgHandler;
import gamemechanics.events.new1.status.OnAddStatusEvent;
import gamemechanics.events.new1.status.OnAddStatusEvent.OnStatusAddHandler;
import gamemechanics.models.entities.Entity;

public class ExShocked extends Status implements OnDmgHandler, OnStatusAddHandler {

	public ExShocked(Entity source, Entity target) {
		super(source, target);
	}

	@Override
	public HandlerType type() {
		return HandlerType.Modifier;
	}

	@Override
	public void onDmg(OnDmgEvent e) {
		
	}
	
	@Override
	public void onStatusAdd(OnAddStatusEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int id() {
		return 0;
	}

	@Override
	public boolean fuse(Status s) {
		return false;
	}

	@Override
	public void onAdd() {
		
	}

	@Override
	public void onLose() {
		
	}

	
}
