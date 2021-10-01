package com.souchy.randd.ebi.ammolite.spells.fire;

import com.souchy.randd.commons.tealwaters.ecs.Engine;
import com.souchy.randd.commons.tealwaters.logging.Log;
import com.souchy.randd.data.s1.spells.fire.Meteor;
import com.souchy.randd.ebi.ammolite.Ammolite;
import com.souchy.randd.ebi.ammolite.FXPlayer;
import com.souchy.randd.ebi.ammolite.FXPlayer.FXInterpolation;
import com.souchy.randd.ebi.ammolite.FXPlayer.FXInterpolationV2;
import com.souchy.randd.ebi.ammolite.spells.SimpleOnCastFX;

public class MeteorFX extends SimpleOnCastFX {

	public MeteorFX(Engine engine) {
		super(engine);
	}

	@Override
	public Class<?> modelClass() {
		return Meteor.class;
	}

	@Override
	protected String getFxPath() {
		return "fx/fire/meteor.efk";
	}
	
}
