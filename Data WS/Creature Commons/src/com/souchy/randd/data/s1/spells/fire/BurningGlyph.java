package com.souchy.randd.data.s1.spells.fire;

import java.util.HashMap;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.effects.status.AddStatusEffect;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.models.TerrainEffect;
import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.statics.CreatureType;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.commons.diamond.statusevents.other.EnterCellEvent;
import com.souchy.randd.commons.diamond.statusevents.other.LeaveCellEvent;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent;
import com.souchy.randd.commons.diamond.statusevents.other.WalkEvent;
import com.souchy.randd.commons.diamond.statusevents.other.EnterCellEvent.OnEnterCellHandler;
import com.souchy.randd.commons.diamond.statusevents.other.LeaveCellEvent.OnLeaveCellHandler;
import com.souchy.randd.commons.diamond.statusevents.other.TurnStartEvent.OnTurnStartHandler;
import com.souchy.randd.commons.diamond.statusevents.other.WalkEvent.OnWalkHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.data.s1.status.Burning;
import com.souchy.randd.jade.Constants;


/**
 * Applies a glyph (status) to the ground
 * 
 * @author Blank
 * @date 17 août 2021
 */
public class BurningGlyph extends Spell {

	public static class BurningGlyphStatus extends TerrainEffect implements OnWalkHandler, OnEnterCellHandler, OnLeaveCellHandler, OnTurnStartHandler {
		public BurningGlyphStatus(Fight fight, int sourceEntityId, int targetCellId) {
			super(fight, sourceEntityId, targetCellId);
		}
		@Override
		public int modelid() {
			return Constants.statusId(BurningGlyph.modelid);
		}
		@Override
		public HandlerType type() {
			return HandlerType.Reactor;
		}
		@Override
		public void onTurnStart(TurnStartEvent event) {
			
		}
		@Override
		public void onLeaveCell(LeaveCellEvent event) {
			
		}
		@Override
		public void onEnterCell(EnterCellEvent event) {
			
		}
		@Override
		public void onWalk(WalkEvent event) {
			
		}
		@Override
		public BurningGlyphStatus create(Fight f, int entitySourceId, int entityTargetId) {
			return new BurningGlyphStatus(f, entitySourceId, entityTargetId);
		}
		@Override
		public Status copy0(Fight f) {
			var s = new BurningGlyphStatus(f, sourceEntityId, this.targetEntityId);
			return s;
		}
	}

	
	public static final int modelid = Elements.fire.makeid(1, 8);

	public AddStatusEffect e1;
	
	
	public BurningGlyph(Fight f) {
		super(f);
		var formula = new HashMap<Element, IntStat>();
		formula.put(Elements.fire, new IntStat(50, 0, 10, 0));
		e1 = new AddStatusEffect(AoeBuilders.single.get(), TargetType.full.asStat(), (ff) -> {
			var b = new Burning(ff, 0, 0);
			b.duration = 3;
			b.stacks = 1;
			b.canDebuff = true;
			b.canRemove = true;
			return b;
		});
		this.effects.add(e1);
	}
	
	@Override
	public int modelid() {
		return modelid;
	}

	@Override
	protected void initBaseStats(SpellStats stats) {
		stats.costs.put(Resource.mana, new IntStat(3));
		stats.maxRangeRadius.baseflat = 8;
	}

	@Override
	protected ImmutableList<Element> initElements() {
		return ImmutableList.of(Elements.fire);
	}

	@Override
	protected ImmutableList<CreatureType> initCreatureTypes() {
		return ImmutableList.of();
	}

	@Override
	public void cast0(Creature caster, Cell target) {
		Log.info("BurningGlyph cast: " + caster + ", " + target);
		e1.apply(caster, target);
	}
	
	@Override
	public Spell copy(Fight fight) {
		var s = new BurningGlyph(fight);
		s.stats = stats.copy();
//		Log.critical("BurningGlyph FIGHT COPY = " + fight);
		return s;
	}

	
}
