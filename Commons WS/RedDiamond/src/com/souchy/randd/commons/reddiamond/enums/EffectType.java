package com.souchy.randd.commons.reddiamond.enums;

public enum EffectType {
	
	Kill(000),
	ResourceGainLoss(001),
	Summon(002),
	PassTurnTarget(003),
	
	
	Damage(101),
	Poison(102), // same as damage, but may not proc certain events (ex dofus goes through shields)
	
	
	Dash(202),
	DasthTo(203),
	Move(204),
	Pull(205),
	PullTo(206),
	Push(207),
	PushTo(28),
	Switch(209),
	Teleport(210),
	
	AddStatus(312),
	ModifyStatus(314),
	RemoveStatus(315),
	
	AddTerrain(316),
	ModifyTerrain(317),
	RemoveTerrain(318),
	;
	
	public final int id;
	private EffectType(int id) {
		this.id = id;
	}
}
