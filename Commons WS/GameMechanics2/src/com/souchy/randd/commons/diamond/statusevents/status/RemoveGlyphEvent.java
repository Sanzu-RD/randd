package com.souchy.randd.commons.diamond.statusevents.status;

import com.google.common.eventbus.Subscribe;
import com.souchy.randd.commons.diamond.effects.status.RemoveGlyphEffect;
import com.souchy.randd.commons.diamond.models.Cell;
import com.souchy.randd.commons.diamond.models.Creature;
import com.souchy.randd.commons.diamond.statusevents.Event;
import com.souchy.randd.commons.diamond.statusevents.Handler;

public class RemoveGlyphEvent extends Event {
	
	public interface OnRemoveGlyphHandler extends Handler { 
		@Subscribe
		public default void handle0(RemoveGlyphEvent event) {
			if(check(event)) onRemoveGlyph(event);
		}
		public void onRemoveGlyph(RemoveGlyphEvent event);
	}
	
	public RemoveGlyphEvent(Creature source, Cell target, RemoveGlyphEffect effect) {
		super(source, target, effect);
	}

	@Override
	public RemoveGlyphEvent copy0() {
		return new RemoveGlyphEvent(source, target, (RemoveGlyphEffect) effect.copy());
	}
	
}
