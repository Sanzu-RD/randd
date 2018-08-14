package com.souchy.randd.situationtest.eventshandlers;

import com.souchy.randd.situationtest.events.statschange.HpChangeEvent;

public class OnHpChange implements EventHandler<HpChangeEvent> {

	@Override
	public void handle(HpChangeEvent event) {

		//event.source.getStats().get(HP);
		//event.source.getProperties().get(HP); 
			// -> pourrait en faire un key-value store obscure utilisable sur tous les genre d'Entité même traps ? 
				//ptete mieux pas, juste à avoir un <CharacterEntity>
	} 

}
