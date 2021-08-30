package com.souchy.randd.commons.diamond.models;

import java.util.ArrayList;
import java.util.List;

import com.souchy.randd.commons.diamond.common.Aoe;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.effects.status.RemoveStatusEffect;
import com.souchy.randd.commons.diamond.effects.status.RemoveTerrainEffect;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.commons.tealwaters.logging.Log;

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
	
	public List<Cell> aoe = new ArrayList<>();

	public TerrainEffect(Fight fight, int sourceEntityId, int targetCellId) {
		super(fight, sourceEntityId, targetCellId);
	}
	
	/* mainly for deserialization */
	public abstract TerrainEffect create(Fight fight, int sourceEntityId, int targetEntityId);
	
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
	

	protected void expire() {
		try {
			Log.format("TerrainEffect (%s) expire %s %s", this.id, duration, this);
			var f = get(Fight.class);
			var source = f.creatures.get(sourceEntityId);
//			var target = f.entities.get(targetEntityId);
//			var cell = f.board.get(target);
			
//			var aoe = this.get(Aoe.class); // AoeBuilders.single.get()
//			var e = new RemoveTerrainEffect(aoe, TargetType.full.asStat(), this);
//			e.apply(source, cell); // target.getCell());
			
			var e = new RemoveTerrainEffect(AoeBuilders.single.get(), TargetType.full.asStat(), this);
			this.aoe.forEach(c -> {
				if(c.statuses.hasStatus(this.getClass()))
					e.apply(source, c); // target.getCell());
			});

			// unregister status from engine, systems and status bus
			this.dispose();
		} catch (Exception e) {
			Log.error("", e);
		}
	}
	
	
	
}
