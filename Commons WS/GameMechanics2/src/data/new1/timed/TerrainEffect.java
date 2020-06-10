package data.new1.timed;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.net.netty.bytebuf.BBDeserializer;
import com.souchy.randd.commons.net.netty.bytebuf.BBMessage;
import com.souchy.randd.commons.net.netty.bytebuf.BBSerializer;

import data.new1.ecs.Entity;
import gamemechanics.data.BurningGlyph;
import gamemechanics.models.Cell;
import gamemechanics.models.Fight;
import io.netty.buffer.ByteBuf;

/**
 * Separated from Status hierarchy on 10 june 2020
 * 
 * @author Blank
 * @date mars 2020
 */
public abstract class TerrainEffect extends Entity implements BBSerializer, BBDeserializer { //extends Status {

	public int id;
	public abstract int modelID();
	
	public int sourceEntityId;
	public int targetEntityId;
	public int parentEffectId; //Effect parent;
	
	public int stacks; // scaling effects with stacks?
	public int duration; // 
	public boolean canRemove; // can remove from cell? 
	public boolean canDebuff; // can debuff stacks from cell?
	
	/** this or a toString() / description() ? */
//	public List<Effect> tooltipEffects = new ArrayList<>();
	public List<Integer> effects = new ArrayList<>();
	
	public List<Cell> aoe;

	public TerrainEffect(Fight fight, int sourceEntityId, int targetCellId) {
		super(fight);
		this.sourceEntityId = sourceEntityId;
		this.targetEntityId = targetCellId;
	}
	
	/* mainly for deserialization */
	public abstract TerrainEffect create(Fight fight, int entitySourceId, int entityTargetId);
	

	@Override
	public ByteBuf serialize(ByteBuf out) {
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		return null;
	}
	
}
