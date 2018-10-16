package com.souchy.randd.commons.cache.redisDao;

import com.souchy.randd.commons.tealwaters.commons.Namespace;
import com.souchy.randd.jade.combat.CombatEntity;
import com.souchy.randd.jade.combat.IEffect;

public class EffectDAO implements IEffect {
	
	private Namespace effects = new Namespace.RedisNamespace("");
	
	@Override
	public CombatEntity getSource() {
		return null;
	}

	@Override
	public void apply() {
		
	}

	@Override
	public void getType() {
		
	}

	@Override
	public void getDesc() {
		
	}

	@Override
	public void canStack() {
		
	}

	@Override
	public void canProlong() {
		
	}

	@Override
	public void maxStacks() {
		
	}

	@Override
	public void maxDuration() {
		
	}

	@Override
	public void isVisible() {
		
	}

	@Override
	public void targetFlags() {
		
	}

	@Override
	public void currentStacks() {
		
	}

	@Override
	public void currentDuration() {
		
	}

	@Override
	public void conditions() {
		
	}

}
