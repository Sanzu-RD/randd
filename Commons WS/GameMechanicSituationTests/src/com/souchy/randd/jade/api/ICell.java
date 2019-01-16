package com.souchy.randd.jade.api;

import java.util.List;

import com.google.common.eventbus.EventBus;
import com.souchy.randd.situationtest.math.Point3D;
import com.souchy.randd.situationtest.models.entities.Character;

/**
 * 
 * Is a Cell an Entity ?
 * / **
	 * Not sure if I use a general event bus in the Board, or a seperate eventbus for each cell, (each entity?), etc
	 * Think it can make sense for each cell, but not for each entity ?
	 * Idk, it can work like when I do damage I can do : targets.foreach(t -> t.publish(DamageEvent));
	 * 
	 * @return - This Cell's EventBus responsible for cell events. 
	 * /
 * @author Souchy
 *
 */
public interface ICell extends EventProxy {

	/**
	 * 
	 * @return EventBus
	 */
	public EventBus bus();
	
	/**
	 * 
	 * @return <s>True is the cell type blocks the line of sight</s>
	 */
	public boolean blocksLineOfSight();
	
	/**
	 * 
	 * @return True if you can walk on this cell 
	 */
	public boolean walkable();
	
	/**
	 * 
	 * @return - Position in X, Y, Z
	 */
	public Point3D getPos(); 
	
	/**
	 * 
	 * @return - The combat entity riding this cell, if there is one, null otherwise
	 */
	public Character getCharacter(); //ICombatEntity getCombatEntity(); //FightContext context);

	/**
	 * 
	 * @return - All combat entities riding this cell, ex si un panda porte qqn ou s'il y a une glyphe/pi�ge ? 
	 */
	public List<AEntity> getEntities(); //FightContext context);
	
	/**
	 * 
	 * @return - True if there is a combat entity on the cell
	 */
	public boolean hasCharacter(); //hasCombatEntity(FightContext context);

	/**
	 * we'll see how we want to do this later
	 * @param source
	 */
	public void testSetCharacter(Character source);
	
	
}
