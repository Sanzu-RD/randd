package gamemechanics.data.effects.status;

import java.util.function.Supplier;

import data.new1.Effect;
import data.new1.spellstats.imp.TargetConditionStat;
import data.new1.timed.Status;
import data.new1.timed.TerrainEffect;
import gamemechanics.common.Aoe;
import gamemechanics.events.new1.Event;
import gamemechanics.events.new1.Handler;
import gamemechanics.events.new1.status.OnAddStatusEvent;
import gamemechanics.models.entities.Cell;
import gamemechanics.models.entities.Entity;

public class AddStatusEffect extends Effect {
	
	// pourrait avoir des int pour duration/stacks?
	
	/** status supplier to create a status instance on effect.apply */
	private Supplier<Status> statusBuilder;
	
	public AddStatusEffect(Aoe aoe, TargetConditionStat targetConditions, Supplier<Status> statusBuilder) {
		super(aoe, targetConditions);
		this.statusBuilder = statusBuilder;
	}

	@Override
	public Event createAssociatedEvent(Entity source, Cell target) {
		return new OnAddStatusEvent(source, target, this);
	}

	@Override
	public void prepareCaster(Entity caster, Cell aoeOrigin) {
		
	}
	
	@Override
	public void prepareTarget(Entity caster, Cell target) {
		
	}
	
	@Override
	public void apply0(Entity source, Cell target) {
		// create status instance
		var status = statusBuilder.get();
		status.source = source.ref();
		status.target = target.ref();
		status.parent = this;
		
		// Add status. StatusList manages fusion by itsef
		// FIXME add status to creature or cell 
		// FIXME adding a status to a cell should trigger reactors to apply statuses on creatures 
		// FIXME aka all cells should have a reactor status that adds statuses to creatures on them (applicability determined by the status)
		
		if(status instanceof TerrainEffect) {
			target.getStatus().add(status);
			// register in the eventpipeline if the status is either of interceptors/modifiers/reactors
			target.handlers.register(status);
		} else {
			target.getCreatures().get(0).getStatus().add(status);
			// register in the eventpipeline if the status is either of interceptors/modifiers/reactors
			target.getCreatures().get(0).handlers.register(status);
		}
		
	}
	
	@Override
	public AddStatusEffect copy() {
		return new AddStatusEffect(aoe, targetConditions, statusBuilder);
	}

}
