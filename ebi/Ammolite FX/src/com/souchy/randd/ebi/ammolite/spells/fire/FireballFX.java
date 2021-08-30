package com.souchy.randd.ebi.ammolite.spells.fire;

import java.util.function.Supplier;

import com.souchy.randd.commons.diamond.models.Fight;
import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.spells.fire.Fireball;
import com.souchy.randd.ebi.ammolite.Ammolite;
import com.souchy.randd.ebi.ammolite.FXPlayer;
import com.souchy.randd.ebi.ammolite.spells.SimpleOnCastFX;

import br.com.johnathan.gdx.effekseer.api.ParticleEffekseer;

public class FireballFX extends SimpleOnCastFX {

	public FireballFX(Engine engine) {
		super(engine);
	}

	@Override
	public Class<?> modelClass() {
		return Fireball.class;
	}

	@Override
	protected String getFxPath() {
		return "fx/fire/fire.efk";
	}
	
}
