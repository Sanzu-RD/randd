package com.souchy.randd.ebishoal.sapphire.ux.actions;

import java.util.function.Supplier;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.souchy.randd.ebishoal.sapphire.main.SapphireGame;

/**
 * Used for resource gain/loss like damage and using mana/movement points
 * 
 * @author Blank
 * @date 12 janv. 2021
 */
public class ResourceDecalAction extends TemporalAction {
	
	private final Supplier<Vector3> worldPos;
	public ResourceDecalAction(Supplier<Vector3> worldPos) {
		this.worldPos = worldPos;
		this.setDuration(2.5f);
	}
	@Override
	protected void update(float percent) {
		var pos = worldPos.get();
		pos.z += Math.min(percent, 0.9f) * 2f;
		SapphireGame.gfx.getCamera().project(pos);
		pos.x += 5; // 5 pixels à droite
		this.actor.setPosition(pos.x, pos.y);
	}
	@Override
	protected void end() {
		this.actor.remove(); // enlève l'actor du stage
		reset(); // dispose les références
	}
}