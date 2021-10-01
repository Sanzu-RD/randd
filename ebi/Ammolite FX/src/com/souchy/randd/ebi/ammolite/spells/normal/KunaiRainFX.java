package com.souchy.randd.ebi.ammolite.spells.normal;

import java.util.function.Supplier;

import com.souchy.randd.commons.diamond.models.components.Position;
import com.souchy.randd.commons.diamond.statusevents.other.CastSpellEvent;
import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.creatures.tsukuyo.spells.KunaiNova;
import com.souchy.randd.data.s1.creatures.tsukuyo.spells.KunaiRain;
import com.souchy.randd.ebi.ammolite.Ammolite;
import com.souchy.randd.ebi.ammolite.FXPlayer;
import com.souchy.randd.ebi.ammolite.spells.SimpleOnCastFX;

public class KunaiRainFX extends SimpleOnCastFX {

	
	public KunaiRainFX(Engine engine) {
		super(engine);
	}

	@Override
	public Class<?> modelClass() {
		return KunaiRain.class;
	}

//	@Override
//	public void update(float delta) {
//		fx.setPosition(getTarget.get().x, 0, getTarget.get().y);
//	}

	@Override
	protected String getFxPath() {
		return "fx/normal/kunairain.efk";
	}
	
	
}
