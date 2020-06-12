package gamemechanics.data;

import data.new1.timed.TerrainEffect;
import gamemechanics.events.new1.other.OnEnterCellEvent;
import gamemechanics.events.new1.other.OnEnterCellEvent.OnEnterCellHandler;
import gamemechanics.events.new1.other.OnLeaveCellEvent;
import gamemechanics.events.new1.other.OnLeaveCellEvent.OnLeaveCellHandler;
import gamemechanics.events.new1.other.OnTurnStartEvent;
import gamemechanics.events.new1.other.OnTurnStartEvent.OnTurnStartHandler;
import gamemechanics.events.new1.other.OnWalkEvent;
import gamemechanics.events.new1.other.OnWalkEvent.OnWalkHandler;
import gamemechanics.models.Fight;

public class BurningGlyph extends TerrainEffect implements OnWalkHandler, OnEnterCellHandler, OnLeaveCellHandler, OnTurnStartHandler {

	public BurningGlyph(Fight fight, int entitySourceId, int entityTargetId) {
		super(fight, entitySourceId, entityTargetId);
	}

	@Override
	public int modelID() {
		return 0;
	}

	@Override
	public HandlerType type() {
		return HandlerType.Reactor;
	}

	@Override
	public void onTurnStart(OnTurnStartEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeaveCell(OnLeaveCellEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnterCell(OnEnterCellEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWalk(OnWalkEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BurningGlyph create(Fight f, int entitySourceId, int entityTargetId) {
		return new BurningGlyph(f, entitySourceId, entityTargetId);
	}

	
}
