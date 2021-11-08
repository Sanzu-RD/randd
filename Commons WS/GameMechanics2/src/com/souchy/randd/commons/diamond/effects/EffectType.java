package com.souchy.randd.commons.diamond.effects;

public enum EffectType {
	
	Damage(1),
	
	Dash(2),
	DasthTo(3),
	Move(4),
	Pull(5),
	PullTo(6),
	Push(7),
	PushTo(8),
	Switch(9),
	Teleport(10),
	
	Summon(11),
	
	ResourceGainLoss(12),
	
	AddStatus(12),
	ModifyStatus(14),
	RemoveStatus(15),
	
	AddTerrain(16),
	ModifyTerrain(17),
	RemoveTerrain(18),
	;
	
	public final int id;
	private EffectType(int id) {
		this.id = id;
	}
	
}
