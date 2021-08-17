package com.souchy.randd.commons.diamond.models;

import java.util.List;

/**
 * Separated from Status hierarchy on 10 june 2020
 * 
 * Re-added to Status on 17 aout 2021
 * 
 * @author Blank
 * @date mars 2020
 */
public abstract class TerrainEffect extends Status { //  extends Entity implements BBSerializer, BBDeserializer { //
/*
	public int id;
//	public abstract int modelID();
	
	public int sourceEntityId;
	public int targetEntityId;
	public int parentEffectId; //Effect parent;
	
	public int stacks; // scaling effects with stacks?
	public int duration; // 
	public boolean canRemove; // can remove from cell? 
	public boolean canDebuff; // can debuff stacks from cell?
	
	// this or a toString() / description() ? 
//	public List<Effect> tooltipEffects = new ArrayList<>();
	public List<Integer> effects = new ArrayList<>();
*/
	
	public List<Cell> aoe;

	public TerrainEffect(Fight fight, int sourceEntityId, int targetCellId) {
		super(fight, sourceEntityId, targetCellId);
	}
	
	/* mainly for deserialization */
	public abstract TerrainEffect create(Fight fight, int entitySourceId, int entityTargetId);
	
/*
	@Override
	public ByteBuf serialize(ByteBuf out) {
		return out;
	}

	@Override
	public BBMessage deserialize(ByteBuf in) {
		return null;
	}
*/
	
}
