package com.souchy.randd.ebi.ammolite;

import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;

public abstract class SpellFXPlayer {
//
//	protected int modelid = 0;
//	
//	public int modelid() {
//		return this.modelid;
//	}
	
	public abstract int modelid();
	
	public abstract void play(CastSpellEvent e);
	
	
}
