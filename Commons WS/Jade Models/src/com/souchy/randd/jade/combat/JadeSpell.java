package com.souchy.randd.jade.combat;

public interface JadeSpell {
	
	/** resource costs (life, mana, movement usually) */
	public int[] costs();
	public boolean isInstant();
	public boolean needsLineOfSight();
	public boolean onlyInLine();
	public boolean onlyInDiagonal();
	public int cooldown();
	public int maxUsesPerTurn();
	public int maxUsesPerTurnPerTarget();
	public int minRange();
	public int maxRange();
	
	public int getInt(SpellProperty prop);
	public int[] getInta(SpellProperty prop);
	
	public JadeEffect[] effects();
	
}
