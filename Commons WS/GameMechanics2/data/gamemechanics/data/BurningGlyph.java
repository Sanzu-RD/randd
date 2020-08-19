package gamemechanics.data;

import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.TerrainEffect;
import com.souchy.randd.commons.diamond.statusevents.Handler.HandlerType;
import com.souchy.randd.commons.diamond.statusevents.other.OnEnterCellEvent;
import com.souchy.randd.commons.diamond.statusevents.other.OnLeaveCellEvent;
import com.souchy.randd.commons.diamond.statusevents.other.OnTurnStartEvent;
import com.souchy.randd.commons.diamond.statusevents.other.OnWalkEvent;
import com.souchy.randd.commons.diamond.statusevents.other.OnEnterCellEvent.OnEnterCellHandler;
import com.souchy.randd.commons.diamond.statusevents.other.OnLeaveCellEvent.OnLeaveCellHandler;
import com.souchy.randd.commons.diamond.statusevents.other.OnTurnStartEvent.OnTurnStartHandler;
import com.souchy.randd.commons.diamond.statusevents.other.OnWalkEvent.OnWalkHandler;

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
