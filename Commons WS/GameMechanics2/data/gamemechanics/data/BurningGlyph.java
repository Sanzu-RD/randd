package gamemechanics.data;

import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;

import data.new1.timed.Status;
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
import gamemechanics.models.entities.Entity;
import gamemechanics.models.entities.Entity.EntityRef;
import io.netty.buffer.ByteBuf;

public class BurningGlyph extends TerrainEffect implements OnWalkHandler, OnEnterCellHandler, OnLeaveCellHandler, OnTurnStartHandler {

	public BurningGlyph(EntityRef source, EntityRef target) {
		super(source, target);
	}

	@Override
	public int modelID() {
		return 0;
	}

	@Override
	public boolean fuse(Status s) {
		return false;
	}

	@Override
	public void onAdd() {}

	@Override
	public void onLose() {}

	@Override
	public String texture9Patch() {
		return null;
	}

	@Override
	public String textureCenter() {
		return null;
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
	public ByteBuf serialize(ByteBuf out) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status create(EntityRef source, EntityRef target) {
		return new BurningGlyph(source, target);
	}

	
}
