package com.souchy.randd.commons.diamond.statusevents.other;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.models.Spell;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class CastSpellEvent extends Event {
	
	public Spell spell;

	public CastSpellEvent(Creature source, Cell target, Spell spell) {
		super(source, target, null);
		this.spell = spell;
	}
	
	@Override
	public CastSpellEvent copy0() {
		return new CastSpellEvent(source, target, spell.copy());
	}
	
	public interface OnCastSpellHandler extends Handler { // <OnEnterCellEvent> {
		@Subscribe
		public default void handle0(CastSpellEvent event) {
			if(check(event)) onCastSpell(event);
		}
		
		@Override
		default HandlerType type() {
			return HandlerType.Reactor;
		}
		
		public void onCastSpell(CastSpellEvent event);
	}
	
	@Override
	public String testMessage() {
		return source.id + " casts " + spell.modelid() + " on " + target.pos;
	}
	
	
}
