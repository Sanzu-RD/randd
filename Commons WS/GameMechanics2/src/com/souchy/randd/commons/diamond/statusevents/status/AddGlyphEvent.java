package com.souchy.randd.commons.diamond.statusevents.status;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.status.AddGlyphEffect;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class AddGlyphEvent extends Event {
	
	public interface OnAddGlyphHandler extends Handler { 
		@Subscribe
		public default void handle0(AddGlyphEvent event) {
			if(check(event)) onAddGlyph(event);
		}
		public void onAddGlyph(AddGlyphEvent event);
	}
	
	public AddGlyphEvent(Creature source, Cell target, AddGlyphEffect effect) {
		super(source, target, effect);
	}

	@Override
	public AddGlyphEvent copy0() {
		return new AddGlyphEvent(source, target, (AddGlyphEffect) effect.copy());
	}
	
}
