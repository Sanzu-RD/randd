package gamemechanics.data;

import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.TerrainEffect;
import com.souchy.randd.commons.diamond.statusevents.Handler.HandlerType;
import com.souchy.randd.commons.diamond.statusevents.other.EnterCellEvent;
import com.souchy.randd.commons.diamond.statusevents.other.LeaveCellEvent;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent;
import com.souchy.randd.commons.diamond.statusevents.other.WalkEvent;
import com.souchy.randd.commons.diamond.statusevents.other.EnterCellEvent.OnEnterCellHandler;
import com.souchy.randd.commons.diamond.statusevents.other.LeaveCellEvent.OnLeaveCellHandler;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent.OnTurnStartHandler;
import com.souchy.randd.commons.diamond.statusevents.other.WalkEvent.OnWalkHandler;

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
	public void onTurnStart(TurnStartEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeaveCell(LeaveCellEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnterCell(EnterCellEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWalk(WalkEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BurningGlyph create(Fight f, int entitySourceId, int entityTargetId) {
		return new BurningGlyph(f, entitySourceId, entityTargetId);
	}

	
}
