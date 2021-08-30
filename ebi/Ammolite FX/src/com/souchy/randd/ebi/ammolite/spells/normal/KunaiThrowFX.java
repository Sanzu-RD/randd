package com.souchy.randd.ebi.ammolite.spells.normal;

import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.data.s1.creatures.tsukuyo.spells.KunaiThrow;
import com.souchy.randd.ebi.ammolite.spells.SimpleOnCastFX;

public class KunaiThrowFX extends SimpleOnCastFX {

	public KunaiThrowFX(Engine engine) {
		super(engine);
	}

	@Override
	public Class<?> modelClass() {
		return KunaiThrow.class;
	}

	@Override
	protected String getFxPath() {
		return "fx/normal/kunaithrow.efk";
	}
	
	
}
