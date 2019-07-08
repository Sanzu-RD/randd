package com.souchy.randd.mockingbird.lapismock.cleaner;

import com.github.czyzby.lml.parser.impl.AbstractLmlView;

public class GameScreen extends LapisScreen {


	@Override
	protected void act(float delta) {
		super.act(delta);
	}

	@Override
	protected void updateLight(float delta) {
		super.updateLight(delta);
		
	}

	@Override
	public AbstractLmlView createUI() {
		GameScreenHud view = new GameScreenHud();
		return view;
	}

	@Override
	public void renderParticleEffects() {
		
	}
	
}
