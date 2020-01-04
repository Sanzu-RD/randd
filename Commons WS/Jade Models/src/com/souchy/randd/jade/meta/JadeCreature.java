package com.souchy.randd.jade.meta;

import io.netty.buffer.ByteBuf;

/**
 * Customization of a creature model. 
 * 
 * This represents creatures in decks as well as default creatures instances (like summons)
 * 
 * Jade Model ready for database
 * 
 * @author Blank
 *
 */
public final class JadeCreature {
	
	/** creature model id */
	public int creatureModelID;

	/** in which elements the player allocated how much stats */
	public int[] affinities;
	
	/** selected spell models ids */
	public int[] spellIDs;

	
	
	public ByteBuf serialize(ByteBuf out) {
		out.writeInt(creatureModelID);
		for(int i = 0; i < affinities.length; i++) { // Element.count()
			out.writeInt(affinities[i]);
		}
		for(int i = 0; i < spellIDs.length; i++) { // Constants.numberOfSpells
			out.writeInt(spellIDs[i]);
		}
		return out;
	}
	public JadeCreature deserialize(ByteBuf in) {
		creatureModelID = in.readInt();
		for(int i = 0; i < affinities.length; i++) { // Element.count()
			affinities[i] = in.readInt();
			// should check that affinities values are valid
		}
		for(int i = 0; i < spellIDs.length; i++) { // Constants.numberOfSpells
			spellIDs[i] = in.readInt();
			// should check that spell ids are valid (spells that exist and that the creature can learn)
		}
		return this;
	}
	
	//public int[] itemIDs;
	
	//public String skinModel; // different model name for skins like league of legends
 	
}
