package com.souchy.randd.data.s1.creatures.tsukuyo.spells;

import com.google.common.collect.ImmutableList;
import com.souchy.randd.commons.diamond.common.AoeBuilders;
import com.souchy.randd.commons.diamond.effects.status.AddTerrainEffect;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.models.Status;
import com.souchy.randd.commons.diamond.models.TerrainEffect;
import com.souchy.randd.commons.diamond.models.stats.CreatureStats;
import com.souchy.randd.commons.diamond.models.stats.SpellStats;
import com.souchy.randd.commons.diamond.models.stats.base.IntStat;
import com.souchy.randd.commons.diamond.statics.CreatureType;
import com.souchy.randd.commons.diamond.statics.Element;
import com.souchy.randd.commons.diamond.statics.stats.properties.Resource;
import com.souchy.randd.commons.diamond.statics.stats.properties.spells.TargetType;
import com.souchy.randd.commons.diamond.statusevents.Handler.Reactor;
import com.souchy.randd.commons.diamond.statusevents.other.EnterCellEvent;
import com.souchy.randd.commons.diamond.statusevents.other.EnterCellEvent.OnEnterCellHandler;
import com.souchy.randd.commons.diamond.statusevents.other.LeaveCellEvent;
import com.souchy.randd.commons.diamond.statusevents.other.LeaveCellEvent.OnLeaveCellHandler;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.creatures.tsukuyo.Tsukuyo;
import com.souchy.randd.data.s1.main.Elements;
import com.souchy.randd.jade.Constants;

public class Mist extends Spell {
	
	public static class MistEffect extends TerrainEffect implements Reactor, OnEnterCellHandler, OnLeaveCellHandler {
		public static final int modelid = Constants.statusId(Mist.modelid);
		public MistEffect(Fight fight, int sourceEntityId, int targetCellId) {
			super(fight, sourceEntityId, targetCellId);
			this.duration = 2;
			this.stacks = 1;
			this.creatureStats = new CreatureStats();
			this.creatureStats.invisible.base = true;
		}
		@Override
		public TerrainEffect create(Fight fight, int entitySourceId, int entityTargetId) {
			return new MistEffect(fight, entitySourceId, entityTargetId);
		}
		@Override
		public int modelid() {
			return modelid;
		}
		@Override
		public Status copy0(Fight f) {
			var e = new MistEffect(f, sourceEntityId, targetEntityId);
			return e;
		}
		@Override
		public boolean fuse(Status s) {
			//genericFuseStrategy(s, false, false, false);
			return false;
		}
		@Override
		public void onEnterCell(EnterCellEvent event) {
			Log.info("MistEffect onEnterCell");
			event.source.statuses.addStatus(this);
		}

		@Override
		public void onLeaveCell(LeaveCellEvent event) {
			Log.info("MistEffect onLeaveCell");
			event.source.statuses.removeStatus(this);
		}
	}
	

	public static final int modelid = Elements.air.makeid(1, Tsukuyo.modelid, 4);
	
	public AddTerrainEffect e1;
	
	public Mist(Fight f) {
		super(f);
		e1 = new AddTerrainEffect(AoeBuilders.circle.apply(3), TargetType.allies.asStat(), (ff) -> new MistEffect(ff, 0, 0));
	}

	@Override
	public int modelid() {
		return modelid;
	}

	@Override
	protected void initBaseStats(SpellStats stats) {
		stats.costs.put(Resource.mana, new IntStat(0));
		stats.maxRangeRadius.baseflat = 3;
	}

	@Override
	protected ImmutableList<Element> initElements() {
		return ImmutableList.of(Elements.air);
	}

	@Override
	protected ImmutableList<CreatureType> initCreatureTypes() {
		return ImmutableList.of();
	}

	@Override
	public void cast0(Creature caster, Cell target) {
		Log.info("Mist spell cast0");
		e1.apply(caster, target);
	}

	@Override
	public Spell copy(Fight fight) {
		var s = new Mist(fight);
		s.stats = stats.copy();
		return s;
	}
	
}
