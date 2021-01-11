package com.souchy.randd.ebi.ammolite.spells;

import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.data.s1.spells.fire.Fireball;
import com.souchy.randd.ebi.ammolite.Ammolite;
import com.souchy.randd.ebi.ammolite.SpellFXPlayer;

import particles.ParticleEffekseer;

public class FireballFX extends SpellFXPlayer {

	private static ParticleEffekseer effect;
	
//	public FireballFX() {
//		this.modelid = Fireball.fireballID;
//	}
	
	public void load() {
		try {
			effect = Ammolite.particle();
			effect.load("fx/fire/fire.efk", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dispose() {
		effect.pause();
		effect.delete();
		effect = null;
	}
	
	@Override
	public void play(CastSpellEvent e) {
		if(effect == null) load();
		var pos = e.target.pos;
		effect.setLocation((float) pos.x + 0.5f, 1.5f, (float) -pos.y - 0.5f);
		effect.play();
		effect.setOnAnimationComplete(this::onDone);
	}
	
	private void onDone() {
		dispose();
	}

	@Override
	public int modelid() {
		return Fireball.fireballID;
	}


	
}
