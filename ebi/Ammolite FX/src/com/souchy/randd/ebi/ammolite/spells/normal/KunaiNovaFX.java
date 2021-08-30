package com.souchy.randd.ebi.ammolite.spells.normal;

import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.data.s1.creatures.tsukuyo.spells.KunaiNova;
import com.souchy.randd.ebi.ammolite.spells.SimpleOnCastFX;

public class KunaiNovaFX extends SimpleOnCastFX {
	
	public KunaiNovaFX(Engine engine) {
		super(engine);
	}

	@Override
	public Class<?> modelClass() {
		return KunaiNova.class;
	}

//	@Override
//	public void update(float delta) {
//		fx.setPosition(getTarget.get().x, 0, getTarget.get().y);
//	}

	@Override
	protected String getFxPath() {
		return "fx/normal/kunainova.efk";
	}
	
	
}
